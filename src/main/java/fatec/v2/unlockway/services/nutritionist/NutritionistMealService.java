package fatec.v2.unlockway.services.nutritionist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fatec.v2.unlockway.api.dto.ingredients.CreateIngredientMealDTO;
import fatec.v2.unlockway.api.dto.ingredients.GetIngredientMealDTO;
import fatec.v2.unlockway.api.dto.nutritionistMeal.GetNutritionistMealDTO;
import fatec.v2.unlockway.api.dto.nutritionistMeal.SaveNutritionistMealDTO;
import fatec.v2.unlockway.azure.services.BlobStorage;
import fatec.v2.unlockway.domain.entity.IngredientModel;
import fatec.v2.unlockway.domain.entity.RecommendationModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistMealModel;
import fatec.v2.unlockway.domain.entity.relationalships.MealIngredientModel;
import fatec.v2.unlockway.domain.enums.Measure;
import fatec.v2.unlockway.domain.enums.Status;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.INutritionistMealService;
import fatec.v2.unlockway.domain.repositories.IngredientRepository;
import fatec.v2.unlockway.domain.repositories.MealIngredientsRepository;
import fatec.v2.unlockway.domain.repositories.RecommendationRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistMealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NutritionistMealService implements INutritionistMealService {
    private final RecommendationRepository recommendationRepository;
    private final IngredientRepository ingredientRepository;
    private final NutritionistMealRepository nutritionistMealRepository;
    private final MealIngredientsRepository mealIngredientsRepository;

    @Override
    public List<GetNutritionistMealDTO> getAllMealSuggestions() {
        List<NutritionistMealModel> nutritionistMealModels = nutritionistMealRepository.findAll().stream().toList();
        List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findAll();

        return nutritionistMealModels.stream()
            .map((e) -> convertToResponseDTO(e, mealIngredients.stream().filter((i) -> i.getMealId().equals(e.getId())).toList()))
            .toList();

    }

    @Override
    public GetNutritionistMealDTO findById(UUID id) throws ResourceNotFoundException {
        NutritionistMealModel foundMealSuggestion = nutritionistMealRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Refeição não encontrada!"));

        List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findByMealId(id);

        return convertToResponseDTO(foundMealSuggestion, mealIngredients);
    }

    @Override
    @Transactional
    public GetNutritionistMealDTO createMealSuggestion(SaveNutritionistMealDTO nutritionistMealSuggestionDTO, Optional<MultipartFile> photo) throws ResourceNotFoundException {
        NutritionistMealModel mealToBeSaved = new NutritionistMealModel();

        RecommendationModel recommendation = recommendationRepository.findById(nutritionistMealSuggestionDTO.getIdRecommendation())
                .orElseThrow(() -> new ResourceNotFoundException("Recomendação não encontrada!"));

        // Setting the relationships
        mealToBeSaved.setRecommendationModel(recommendation);
        mealToBeSaved.setNutritionistModel(recommendation.getNutritionistModel());
        mealToBeSaved.setPatientModel(recommendation.getPatientModel());

        // Setting the meal fields
        mealToBeSaved.setName(nutritionistMealSuggestionDTO.getName());
        mealToBeSaved.setDescription(nutritionistMealSuggestionDTO.getDescription());
        mealToBeSaved.setCategory(nutritionistMealSuggestionDTO.getCategory());
        mealToBeSaved.setOriginalMealId(nutritionistMealSuggestionDTO.getOriginalMealId());
        mealToBeSaved.setPreparationMethod(nutritionistMealSuggestionDTO.getPreparationMethod());
        mealToBeSaved.setCreatedAt(LocalDateTime.now());
        mealToBeSaved.setUpdatedAt(LocalDateTime.now());

        // Calculating Total Calories
        double totalCalories = getTotalCalories(nutritionistMealSuggestionDTO);
        mealToBeSaved.setTotalCalories(totalCalories);

        NutritionistMealModel mealSaved;

        //  Implement Azure Communication to save the Blob File (Meal Photo)
        if(photo != null && photo.isPresent()) {
            // Maximum photo size: 1048576 bytes
            NutritionistMealModel mealWithPhotoToBeCreated = StoreMealPhotoIntoAzureBlobStorage(photo.get(), mealToBeSaved);
            mealSaved  = nutritionistMealRepository.save(mealWithPhotoToBeCreated);
        }else{
            deleteMealPhotoFromAzureAndMealModel(mealToBeSaved);

            mealToBeSaved.setPhoto(null);

            mealSaved = nutritionistMealRepository.save(mealToBeSaved);
        }

        var mealIngredientsCreated = ApplyMealIngredients(nutritionistMealSuggestionDTO, mealSaved);

        return convertToResponseDTO(mealSaved, mealIngredientsCreated);
    }

    @Override
    @Transactional
    public GetNutritionistMealDTO updateMealSuggestion(UUID id, SaveNutritionistMealDTO nutritionistMealSuggestionDTO, Optional<MultipartFile> photo) throws ResourceNotFoundException { 
        Optional<NutritionistMealModel> meal = nutritionistMealRepository.findById(id);
        
        if(meal.isEmpty()) throw new ResourceNotFoundException("Essa refeição não existe ainda");
         
        NutritionistMealModel mealToBeUpdated = meal.get();

        if(mealToBeUpdated.getRecommendationModel().getStatus() == Status.APPROVED) {
            throw new ResourceNotFoundException("Impossível editar a Refeição! A Recomendação já foi aceita");
        }
        
        if(mealToBeUpdated.getRecommendationModel().getStatus() == Status.DENIED) {
            throw new ResourceNotFoundException("Impossível editar a Refeição! A Recomendação já foi aceita");
        }

        // Setting the meal fields
        mealToBeUpdated.setName(nutritionistMealSuggestionDTO.getName());
        mealToBeUpdated.setOriginalMealId(nutritionistMealSuggestionDTO.getOriginalMealId());
        mealToBeUpdated.setDescription(nutritionistMealSuggestionDTO.getDescription());
        mealToBeUpdated.setCategory(nutritionistMealSuggestionDTO.getCategory());
        mealToBeUpdated.setPreparationMethod(nutritionistMealSuggestionDTO.getPreparationMethod());
        mealToBeUpdated.setUpdatedAt(LocalDateTime.now());

        // Calculating Total Calories
        double totalCalories = getTotalCalories(nutritionistMealSuggestionDTO);
        mealToBeUpdated.setTotalCalories(totalCalories);

        NutritionistMealModel mealSaved;

        //  Implement Azure Communication to save the Blob File (Meal Photo)
        if(photo != null && photo.isPresent()) {
            // Maximum photo size: 1048576 bytes
            NutritionistMealModel mealWithPhotoToBeCreated = StoreMealPhotoIntoAzureBlobStorage(photo.get(), mealToBeUpdated);
            mealSaved  = nutritionistMealRepository.save(mealWithPhotoToBeCreated);
        }else{
            mealSaved = nutritionistMealRepository.save(mealToBeUpdated);
        }

        var mealIngredientsCreated = ApplyMealIngredients(nutritionistMealSuggestionDTO, mealSaved);

        return convertToResponseDTO(mealSaved, mealIngredientsCreated);
    }

    @Override
    public void deleteMealSuggestion(UUID id) throws ResourceNotFoundException {
        NutritionistMealModel mealSuggestion = nutritionistMealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sugestão de refeição não encontrada"));
        nutritionistMealRepository.delete(mealSuggestion);
    }

    public List<GetNutritionistMealDTO> getMealSuggestionsByPatientId(UUID idPatient) {
        List<NutritionistMealModel> nutritionistMealModels = nutritionistMealRepository.findByPatientModelId(idPatient);
    
        List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findAll();

        return nutritionistMealModels.stream()
            .map((e) -> convertToResponseDTO(e, mealIngredients.stream().filter((i) -> i.getMealId().equals(e.getId())).toList()))
            .toList();
    }

    public List<GetNutritionistMealDTO> getMealSuggestionsByNutritionistId(UUID idNutritionist) {
        List<NutritionistMealModel> nutritionistMealModels = nutritionistMealRepository.findByNutritionistModelId(idNutritionist);
        List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findAll();

        return nutritionistMealModels.stream()
            .map((e) -> convertToResponseDTO(e, mealIngredients.stream().filter((i) -> i.getMealId().equals(e.getId())).toList()))
            .toList();
    }

    public List<GetNutritionistMealDTO> getMealSuggestionByRecommendationId(UUID idRecommendation) {
        List<NutritionistMealModel> nutritionistMealDTOS = nutritionistMealRepository.findByRecommendationId(idRecommendation);
        List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findAll();

        return nutritionistMealDTOS.stream()
                .map((e) -> convertToResponseDTO(e, mealIngredients.stream().filter((i) -> i.getMealId().equals(e.getId())).toList()))
                .toList();
    }

    // Helpers
    public static double calculateCalories(double amount, Measure measure, double calories) {
        if (measure == Measure.AMOUNT) {
            // Se MEASURE for igual a "quantity", retorna calories vezes o valor de amount
            return calories * amount;
        } else {
            // Se MEASURE for diferente, calcula 1% de calories e multiplica pelo amount
            double percent = 0.01 * calories;
            return percent * amount;
        }
    }

    private double getTotalCalories(SaveNutritionistMealDTO nutritionistMealSuggestionDTO) throws ResourceNotFoundException {
        double totalCalories = 0;

        for (CreateIngredientMealDTO ingredientDTO : nutritionistMealSuggestionDTO.getIngredients()) {
                IngredientModel ingredient = ingredientRepository.findById(ingredientDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado"));
            double calculateTotalCalories = calculateCalories(ingredientDTO.getAmount(), ingredient.getMeasure(), ingredient.getCalories());
            totalCalories += calculateTotalCalories;
            }

        return totalCalories;
    }

    private NutritionistMealModel StoreMealPhotoIntoAzureBlobStorage(MultipartFile photo, NutritionistMealModel mealToBeSaved) {
       deleteMealPhotoFromAzureAndMealModel(mealToBeSaved);
       String containerName = "meals";

       String filename = BlobStorage.storePhotoIntoAzureBlobStorage(photo, containerName);
       // TODO:Apply the right blob storage URL
       mealToBeSaved.setPhoto("<Blob Storage https link here>" + containerName + "/" + filename);

       return mealToBeSaved;
    }

    private void deleteMealPhotoFromAzureAndMealModel(NutritionistMealModel meal) {
        var oldPhoto = meal.getPhoto();

        if(oldPhoto != null) {
            // TODO:Apply the right blob storage URL
            oldPhoto = oldPhoto.substring("<Blob Storage https link here>".length());
            BlobStorage.removePhotoFromAzureBlobStorage(oldPhoto, "meals");
        }
    }

    private static GetNutritionistMealDTO convertToResponseDTO(NutritionistMealModel mealSaved, List<MealIngredientModel> mealIngredientsCreated) {
        GetNutritionistMealDTO meal = new GetNutritionistMealDTO();

        meal.setId(mealSaved.getId());

       meal.setIdNutritionist(mealSaved.getNutritionistModel().getId());
       meal.setIdPatient(mealSaved.getPatientModel().getId());
       meal.setIdRecommendation(mealSaved.getRecommendationModel().getId());
       meal.setOriginalMealId(mealSaved.getOriginalMealId());

        meal.setName(mealSaved.getName());
        meal.setPhoto(mealSaved.getPhoto());
        meal.setDescription(mealSaved.getDescription());
        meal.setIngredients(mealIngredientsCreated.stream().map((e)-> {
            return GetIngredientMealDTO.builder()
                    .name(e.getIngredient().getName())
                    .measure(e.getIngredient().getMeasure())
                    .amount(e.getAmount())
                    .id(e.getIngredient().getId()).build();
        }).toList());

        meal.setCategory(mealSaved.getCategory());
        meal.setPreparationMethod(mealSaved.getPreparationMethod());
        meal.setTotalCalories(mealSaved.getTotalCalories());
        meal.setCreatedAt(mealSaved.getCreatedAt());
        meal.setUpdatedAt(mealSaved.getUpdatedAt());

        return meal;
    }

    private List<MealIngredientModel> ApplyMealIngredients(SaveNutritionistMealDTO mealSuggestionDTO, NutritionistMealModel mealSaved) throws ResourceNotFoundException {

        mealIngredientsRepository.deleteAllByMealId(mealSaved.getId());

        List<MealIngredientModel> mealIngredients = new ArrayList<>();

        for (CreateIngredientMealDTO ingredientDTO : mealSuggestionDTO.getIngredients()) {
            IngredientModel ingredient = ingredientRepository.findById(ingredientDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("Ingrediente não encontrado"));

            MealIngredientModel mealIngredientModel = new MealIngredientModel();

            mealIngredientModel.setAmount(ingredientDTO.getAmount());
            mealIngredientModel.setIngredient(ingredient);
            mealIngredientModel.setMealId(mealSaved.getId());

            mealIngredients.add(mealIngredientModel);
        }

        return mealIngredientsRepository.saveAll(mealIngredients);
    }
}
