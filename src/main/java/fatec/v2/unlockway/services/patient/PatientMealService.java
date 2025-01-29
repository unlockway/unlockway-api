package fatec.v2.unlockway.services.patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fatec.v2.unlockway.api.dto.ingredients.CreateIngredientMealDTO;
import fatec.v2.unlockway.api.dto.ingredients.GetIngredientMealDTO;
import fatec.v2.unlockway.api.dto.patientMeal.GetPatientMealDTO;
import fatec.v2.unlockway.api.dto.patientMeal.SavePatientMealDTO;
import fatec.v2.unlockway.azure.services.BlobStorage;
import fatec.v2.unlockway.domain.entity.IngredientModel;
import fatec.v2.unlockway.domain.entity.patient.PatientMealModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.relationalships.MealIngredientModel;
import fatec.v2.unlockway.domain.enums.MealCategory;
import fatec.v2.unlockway.domain.enums.Measure;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IPatientMealService;
import fatec.v2.unlockway.domain.repositories.IngredientRepository;
import fatec.v2.unlockway.domain.repositories.MealIngredientsRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientMealRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Service
@AllArgsConstructor
@Builder
public class PatientMealService implements IPatientMealService {
    private final PatientMealRepository mealRepository;
    private final IngredientRepository ingredientRepository;
    private final PatientRepository patientRepository;
    private final MealIngredientsRepository mealIngredientsRepository;

    @Override
    public GetPatientMealDTO findById(UUID id) throws ResourceNotFoundException {
        PatientMealModel meal = mealRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Refeição não encontrada"));

        List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findByMealId(id);

        return InnerJoinMealWithIngredients(meal, mealIngredients);
    }

    @Override
    public List<GetPatientMealDTO> findBypatientId(UUID patientId) {
        List<PatientMealModel> meals = mealRepository.findByPatientModelId(patientId).stream().toList();

        System.err.println(meals.size());

        return meals.stream().map((meal)-> {
            List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findByMealId(meal.getId());
            return InnerJoinMealWithIngredients(meal, mealIngredients);
        }).toList();
    }

    @Override
    public List<GetPatientMealDTO> findByName(UUID patientId, String name) {

        var meals = mealRepository.findByName(patientId, name.toLowerCase()).stream().toList();

        return meals.stream().map((meal)-> {
            List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findByMealId(meal.getId());
            return InnerJoinMealWithIngredients(meal, mealIngredients);
        }).toList();
    }

    @Override
    public List<GetPatientMealDTO> findByCategory(UUID patientId, MealCategory mealCategory) {

        var meals = mealRepository.findByCategory(patientId, mealCategory).stream().toList();

        return meals.stream().map((meal)-> {
            List<MealIngredientModel> mealIngredients = mealIngredientsRepository.findByMealId(meal.getId());
            return InnerJoinMealWithIngredients(meal, mealIngredients);
        }).toList();
    }

    @Override
    @Transactional
    public GetPatientMealDTO createMeal(SavePatientMealDTO saveMealDTO, Optional<MultipartFile> photo) throws ResourceNotFoundException {
        PatientMealModel mealToBeSaved = new PatientMealModel();

        // Setting properties
        mealToBeSaved.setName(saveMealDTO.getName());
        mealToBeSaved.setDescription(saveMealDTO.getDescription());
        mealToBeSaved.setCategory(saveMealDTO.getCategory());
        mealToBeSaved.setPreparationMethod(saveMealDTO.getPreparationMethod());
        mealToBeSaved.setCreatedAt(LocalDateTime.now());
        mealToBeSaved.setUpdatedAt(LocalDateTime.now());

        // Finding patient
        Optional<PatientModel> patient = patientRepository.findById(saveMealDTO.getPatientId());

        if(patient.isPresent()) {
            mealToBeSaved.setPatientModel(patient.get());
        }else{
            throw new ResourceNotFoundException("Usuário referenciado na refeição não existe");
        }

        // Calculating Total Calories
        double totalCalories = getTotalCalories(saveMealDTO);

        mealToBeSaved.setTotalCalories(totalCalories);
        PatientMealModel mealSaved;

        //  Implement Azure Communication to save the Blob File (Meal Photo)
        if(photo != null && photo.isPresent()) {
            // Maximum photo size: 1048576 bytes
            PatientMealModel mealWithPhotoToBeCreated = StoreMealPhotoIntoAzureBlobStorage(photo.get(), mealToBeSaved);
            mealSaved  = mealRepository.save(mealWithPhotoToBeCreated);
        }else{
            deleteMealPhotoFromAzureAndMealModel(mealToBeSaved);

            mealToBeSaved.setPhoto(null);

            mealSaved = mealRepository.save(mealToBeSaved);
        }

        var mealIngredientsCreated = ApplyMealIngredients(saveMealDTO, mealSaved);

        return InnerJoinMealWithIngredients(mealSaved, mealIngredientsCreated);
    }

    @Override
    @Transactional
    public GetPatientMealDTO updateMeal(SavePatientMealDTO saveMealDTO, Optional<MultipartFile> photo) throws ResourceNotFoundException {
        Optional<PatientMealModel> meal = mealRepository.findById(saveMealDTO.getId());

        if(meal.isEmpty()) throw new ResourceNotFoundException("Essa refeição não existe ainda");
        
        PatientMealModel mealToBeUpdated = meal.get();

        // Setting properties
        mealToBeUpdated.setName(saveMealDTO.getName());
        mealToBeUpdated.setDescription(saveMealDTO.getDescription());
        mealToBeUpdated.setCategory(saveMealDTO.getCategory());
        mealToBeUpdated.setPreparationMethod(saveMealDTO.getPreparationMethod());
        mealToBeUpdated.setUpdatedAt(LocalDateTime.now());

        // Calculating Total Calories
        double totalCalories = getTotalCalories(saveMealDTO);

        mealToBeUpdated.setTotalCalories(totalCalories);

        PatientMealModel mealSaved;

        //  Implement Azure Communication to save the Blob File (Meal Photo)
        if(photo != null && photo.isPresent()) {
            // Maximum photo size: 1048576 bytes
            PatientMealModel mealWithPhotoToBeCreated = StoreMealPhotoIntoAzureBlobStorage(photo.get(), mealToBeUpdated);
            mealSaved  = mealRepository.save(mealWithPhotoToBeCreated);
        }else{
            mealSaved = mealRepository.save(mealToBeUpdated);
        }

        var mealIngredientsCreated = ApplyMealIngredients(saveMealDTO, mealSaved);

        return InnerJoinMealWithIngredients(mealSaved, mealIngredientsCreated);
    }

    @Override
    @Transactional
    public String deleteMeal(UUID id) throws ResourceNotFoundException {
        PatientMealModel mealToBeDeleted = mealRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Essa refeição não existe e portanto não pode ser deletada"));
        deleteMealPhotoFromAzureAndMealModel(mealToBeDeleted);

        mealIngredientsRepository.deleteAllByMealId(id);
        mealRepository.deleteById(id);
        return "Refeição deletada";
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

    private double getTotalCalories(SavePatientMealDTO saveMealDTO) throws ResourceNotFoundException {
        double totalCalories = 0;

        for (CreateIngredientMealDTO ingredientDTO : saveMealDTO.getIngredients()) {
                IngredientModel ingredient = ingredientRepository.findById(ingredientDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado"));
            double calculateTotalCalories = calculateCalories(ingredientDTO.getAmount(), ingredient.getMeasure(), ingredient.getCalories());
            totalCalories += calculateTotalCalories;
            }

        return totalCalories;
    }

    private PatientMealModel StoreMealPhotoIntoAzureBlobStorage(MultipartFile photo, PatientMealModel mealToBeSaved) {
       deleteMealPhotoFromAzureAndMealModel(mealToBeSaved);
       String containerName = "meals";

       String filename = BlobStorage.storePhotoIntoAzureBlobStorage(photo, containerName);
       // TODO:Apply the right blob storage URL
       mealToBeSaved.setPhoto("<Blob Storage https link here>" + containerName + "/" + filename);

       return mealToBeSaved;
    }

    private void deleteMealPhotoFromAzureAndMealModel(PatientMealModel meal) {
        var oldPhoto = meal.getPhoto();

        if(oldPhoto != null) {
            // TODO:Apply the right blob storage URL
            oldPhoto = oldPhoto.substring("<Blob Storage https link here>".length());
            BlobStorage.removePhotoFromAzureBlobStorage(oldPhoto, "meals");
        }
    }

    private static GetPatientMealDTO InnerJoinMealWithIngredients(PatientMealModel mealSaved, List<MealIngredientModel> mealIngredientsCreated) {
        GetPatientMealDTO meal = new GetPatientMealDTO();

        meal.setId(mealSaved.getId());
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

    private List<MealIngredientModel> ApplyMealIngredients(SavePatientMealDTO saveMealDTO, PatientMealModel mealSaved) throws ResourceNotFoundException {

        mealIngredientsRepository.deleteAllByMealId(mealSaved.getId());

        List<MealIngredientModel> mealIngredients = new ArrayList<>();

        for (CreateIngredientMealDTO ingredientDTO : saveMealDTO.getIngredients()) {
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
