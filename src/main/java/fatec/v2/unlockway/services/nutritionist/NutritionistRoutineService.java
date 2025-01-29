package fatec.v2.unlockway.services.nutritionist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fatec.v2.unlockway.api.dto.nutritionistRoutine.GetNutritionistRoutineDTO;
import fatec.v2.unlockway.api.dto.nutritionistRoutine.SaveNutritionistRoutineDTO;
import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.CreatePatientMealsRoutineDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.GetPatientMealsRoutineDTO;
import fatec.v2.unlockway.domain.entity.RecommendationModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistMealModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistRoutineModel;
import fatec.v2.unlockway.domain.entity.patient.PatientMealModel;
import fatec.v2.unlockway.domain.entity.relationalships.MealIngredientModel;
import fatec.v2.unlockway.domain.entity.relationalships.NutritionistRoutineMealModel;
import fatec.v2.unlockway.domain.enums.Status;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.INutritionistRoutineService;
import fatec.v2.unlockway.domain.repositories.MealIngredientsRepository;
import fatec.v2.unlockway.domain.repositories.RecommendationRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistMealRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRoutineMealRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRoutineRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientMealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NutritionistRoutineService implements INutritionistRoutineService {
    private final NutritionistRoutineRepository nutritionistRoutineRepository;
    private final RecommendationRepository recommendationRepository;
    private final NutritionistMealRepository nutritionistMealRepository;
    private final PatientMealRepository patientMealRepository;
    private final NutritionistRoutineMealRepository nutritionistRoutineMealRepository;
    private final MealIngredientsRepository mealIngredientsRepository;

	@Override
	public List<GetNutritionistRoutineDTO> getRoutineSuggestionsByPatientIdAndNutritionistId(UUID patientId,
			UUID nutritionistId) throws ResourceNotFoundException {
        List<NutritionistRoutineModel> routineSuggestions = nutritionistRoutineRepository.findAllByPatientIdAndNutritionistId(patientId, nutritionistId);
        return generateRoutineResponse(routineSuggestions);
	}

    @Override
    public List<GetNutritionistRoutineDTO> getAllRoutineSuggestions() {
        List<NutritionistRoutineModel> routineSuggestions = nutritionistRoutineRepository.findAll();
        return generateRoutineResponse(routineSuggestions);
    }

    @Override
    public GetNutritionistRoutineDTO findById(UUID id) throws ResourceNotFoundException {
        NutritionistRoutineModel foundedRoutineSuggestion = nutritionistRoutineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rotina não encontrada"));

         return generateRoutineResponse(List.of(foundedRoutineSuggestion)).get(0);
    }

    @Override
    @Transactional
    public GetNutritionistRoutineDTO createRoutineSuggestion(SaveNutritionistRoutineDTO createRoutineDTO) throws ResourceNotFoundException {
        NutritionistRoutineModel routine = new NutritionistRoutineModel();
         
        routine.setName(createRoutineDTO.getName());
        routine.setInUsage(false);
        routine.setOriginalRoutineId(createRoutineDTO.getOriginalRoutineId());
        routine.setMonday(createRoutineDTO.getWeekRepetitions().getMonday());
        routine.setTuesday(createRoutineDTO.getWeekRepetitions().getTuesday());
        routine.setWednesday(createRoutineDTO.getWeekRepetitions().getWednesday());
        routine.setThursday(createRoutineDTO.getWeekRepetitions().getThursday());
        routine.setFriday(createRoutineDTO.getWeekRepetitions().getFriday());
        routine.setSaturday(createRoutineDTO.getWeekRepetitions().getSaturday());
        routine.setSunday(createRoutineDTO.getWeekRepetitions().getSunday());
        routine.setCreatedAt(LocalDateTime.now());
        routine.setUpdatedAt(LocalDateTime.now());

        RecommendationModel recommendation = recommendationRepository.findById(createRoutineDTO.getIdRecommendation())
                .orElseThrow(() -> new RuntimeException("Recomendação não encontrada"));

        routine.setRecommendationModel(recommendation);
        routine.setNutritionistModel(recommendation.getNutritionistModel());
        routine.setPatientModel(recommendation.getPatientModel());

        List<NutritionistRoutineMealModel> NutritionistRoutineMealModelList = new ArrayList<>();

        for (CreatePatientMealsRoutineDTO item : createRoutineDTO.getMeals()) {
            NutritionistRoutineMealModel routineMeal = new NutritionistRoutineMealModel();
            
            routineMeal.setRoutine(routine);
            routineMeal.setNotifyAt(item.getNotifyAt());

            Optional<NutritionistMealModel> nutritionistMealFound = nutritionistMealRepository.findById(item.getIdMeal());

            if (nutritionistMealFound.isEmpty()) {
                 Optional<PatientMealModel> mealFoundPatient = patientMealRepository.findById(item.getIdMeal());
                 
                 if(mealFoundPatient.isEmpty()) {
                    throw new ResourceNotFoundException("Refeição não encontrada");
                 }

                 var meal = mealFoundPatient.get();

                Optional<NutritionistMealModel> indeedFoundNutritionistMealToUseInsteadOfFromPatient = nutritionistMealRepository.findBySeveralProperties(
                    meal.getName(),
                    meal.getDescription(),
                    meal.getPreparationMethod(),
                    meal.getId(),
                    meal.getPhoto(),
                    meal.getTotalCalories()
                );

                if(!indeedFoundNutritionistMealToUseInsteadOfFromPatient.isEmpty()) {
                    routineMeal.setMeal(indeedFoundNutritionistMealToUseInsteadOfFromPatient.get());
                }else{
                    NutritionistMealModel convertedMealFromPatientToNutritionist = NutritionistMealModel.builder()
                        .name(meal.getName())
                        .description(meal.getDescription())
                        .category(meal.getCategory())
                        .totalCalories(meal.getTotalCalories())
                        .preparationMethod(meal.getPreparationMethod())
                        .photo(meal.getPhoto())
                        .nutritionistModel(recommendation.getNutritionistModel())
                        .patientModel(meal.getPatientModel())
                        .originalMealId(meal.getId())
                        .recommendationModel(recommendation)
                        .build();
               
                    NutritionistMealModel createdConvertedMealFromPatientToNutritionist = nutritionistMealRepository.save(convertedMealFromPatientToNutritionist);
                     
                    routineMeal.setMeal(createdConvertedMealFromPatientToNutritionist);

                    cloneMealIngredients(meal, createdConvertedMealFromPatientToNutritionist);
                    
                }
      
                 
            }else{
                var meal = nutritionistMealFound.get();
                routineMeal.setMeal(meal);
            }

            NutritionistRoutineMealModelList.add(routineMeal);
        }


        NutritionistRoutineModel createdRoutine = nutritionistRoutineRepository.save(routine);

        List<NutritionistRoutineMealModel> createdRoutineMeals = nutritionistRoutineMealRepository.saveAll(NutritionistRoutineMealModelList);

        return GetNutritionistRoutineDTO.builder()
            .id(createdRoutine.getId())
            .idRecommendation(createdRoutine.getRecommendationModel().getId())
            .idNutritionist(createdRoutine.getNutritionistModel().getId())
            .idPatient(createdRoutine.getNutritionistModel().getId())
            .originalRoutineId(createdRoutine.getOriginalRoutineId())
            .name(createRoutineDTO.getName())
            .inUsage( createRoutineDTO.getInUsage())
            .meals(getMealsRoutineDTO(createdRoutineMeals))
            .weekRepetitions(createRoutineDTO.getWeekRepetitions())
            .totalCaloriesInTheDay(getMealsRoutineDTO(createdRoutineMeals).stream().map(GetPatientMealsRoutineDTO::getTotalCalories).reduce(0.0, Double::sum))
            .inUsage(false)
            .createdAt(createdRoutine.getCreatedAt())
            .updatedAt(createdRoutine.getUpdatedAt())
            .build();
    }

    @Override
    @Transactional
    public void updateRoutineSuggestion(UUID id, SaveNutritionistRoutineDTO updateRoutineDTO) throws ResourceNotFoundException {

        NutritionistRoutineModel routineToBeUpdated = nutritionistRoutineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rotina não encontrada"));


        if(routineToBeUpdated.getRecommendationModel().getStatus() == Status.APPROVED) {
            throw new ResourceNotFoundException("Impossível editar a Rotina! A Recomendação já foi aceita");
        }
        
        if(routineToBeUpdated.getRecommendationModel().getStatus() == Status.DENIED) {
            throw new ResourceNotFoundException("Impossível editar a Rotina! A Recomendação já foi aceita");
        }

        routineToBeUpdated.setName(updateRoutineDTO.getName());
        routineToBeUpdated.setInUsage(updateRoutineDTO.getInUsage());
        routineToBeUpdated.setOriginalRoutineId(updateRoutineDTO.getOriginalRoutineId());
        routineToBeUpdated.setMonday(updateRoutineDTO.getWeekRepetitions().getMonday());
        routineToBeUpdated.setTuesday(updateRoutineDTO.getWeekRepetitions().getTuesday());
        routineToBeUpdated.setWednesday(updateRoutineDTO.getWeekRepetitions().getWednesday());
        routineToBeUpdated.setThursday(updateRoutineDTO.getWeekRepetitions().getThursday());
        routineToBeUpdated.setFriday(updateRoutineDTO.getWeekRepetitions().getFriday());
        routineToBeUpdated.setSaturday(updateRoutineDTO.getWeekRepetitions().getSaturday());
        routineToBeUpdated.setSunday(updateRoutineDTO.getWeekRepetitions().getSunday());
        routineToBeUpdated.setUpdatedAt(LocalDateTime.now());

        nutritionistRoutineRepository.deleteAllRoutineMealsByRoutineId(routineToBeUpdated.getId());
        
        List<NutritionistRoutineMealModel> NutritionistRoutineMealModelList = new ArrayList<>();

        for (CreatePatientMealsRoutineDTO item : updateRoutineDTO.getMeals()) {
            NutritionistRoutineMealModel routineMeal = new NutritionistRoutineMealModel();
            
            routineMeal.setRoutine(routineToBeUpdated);
            routineMeal.setNotifyAt(item.getNotifyAt());

            Optional<NutritionistMealModel> nutritionistMealFound = nutritionistMealRepository.findById(item.getIdMeal());

            if (nutritionistMealFound.isEmpty()) {

                Optional<PatientMealModel> mealFoundPatient = patientMealRepository.findById(item.getIdMeal());

                if(mealFoundPatient.isEmpty()) {
                    throw new ResourceNotFoundException("Refeição não encontrada");
                }

                var meal = mealFoundPatient.get();

                Optional<NutritionistMealModel> indeedFoundNutritionistMealToUseInsteadOfFromPatient = nutritionistMealRepository.findBySeveralProperties(
                    meal.getName(),
                    meal.getDescription(),
                    meal.getPreparationMethod(),
                    meal.getId(),
                    meal.getPhoto(),
                    meal.getTotalCalories()
                );

                if(!indeedFoundNutritionistMealToUseInsteadOfFromPatient.isEmpty()) {
                    routineMeal.setMeal(indeedFoundNutritionistMealToUseInsteadOfFromPatient.get());
                }else{
                    NutritionistMealModel convertedMealFromPatientToNutritionist = NutritionistMealModel.builder()
                        .name(meal.getName())
                        .description(meal.getDescription())
                        .category(meal.getCategory())
                        .totalCalories(meal.getTotalCalories())
                        .preparationMethod(meal.getPreparationMethod())
                        .photo(meal.getPhoto())
                        .nutritionistModel(routineToBeUpdated.getNutritionistModel())
                        .patientModel(meal.getPatientModel())
                        .originalMealId(meal.getId())
                        .recommendationModel(routineToBeUpdated.getRecommendationModel())
                        .build();

                    NutritionistMealModel createdConvertedMealFromPatientToNutritionist = nutritionistMealRepository.save(convertedMealFromPatientToNutritionist);

                    routineMeal.setMeal(createdConvertedMealFromPatientToNutritionist);

                    cloneMealIngredients(meal, createdConvertedMealFromPatientToNutritionist);
                }
      
            }else{
                var meal = nutritionistMealFound.get();
                routineMeal.setMeal(meal);
            }

            NutritionistRoutineMealModelList.add(routineMeal);
        }

        nutritionistRoutineRepository.save(routineToBeUpdated);

        nutritionistRoutineMealRepository.saveAll(NutritionistRoutineMealModelList);
    }

    private void cloneMealIngredients(PatientMealModel meal,
            NutritionistMealModel createdConvertedMealFromPatientToNutritionist) {
        // Clone the Meal Ingredients from Patient to Nutritionist
        List<MealIngredientModel> ingredientsToClone = mealIngredientsRepository.findByMealId(meal.getId());

        List<MealIngredientModel> mealIngredients = new ArrayList<>();

        for (MealIngredientModel ingredientToClone : ingredientsToClone) {
            MealIngredientModel mealIngredientModel = new MealIngredientModel();

            mealIngredientModel.setAmount(ingredientToClone.getAmount());
            mealIngredientModel.setIngredient(ingredientToClone.getIngredient());
            mealIngredientModel.setMealId(createdConvertedMealFromPatientToNutritionist.getId());

            mealIngredients.add(mealIngredientModel);
        }

        mealIngredientsRepository.saveAll(mealIngredients);
    }

    @Override
    public void deleteRoutineSuggestion(UUID id) throws ResourceNotFoundException {
        NutritionistRoutineModel routineSuggestion = nutritionistRoutineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rotina não encontrada"));
        nutritionistRoutineRepository.delete(routineSuggestion);
    }

    public List<GetNutritionistRoutineDTO> getRoutineSuggestionByPatientId(UUID idPatient) {
        List<NutritionistRoutineModel> routineSuggestions = nutritionistRoutineRepository.findAllByPatientId(idPatient);
        return generateRoutineResponse(routineSuggestions);
    }

    public List<GetNutritionistRoutineDTO> getRoutineSuggestionByNutritionistId(UUID idNutritionist) {
        List<NutritionistRoutineModel> routineSuggestions = nutritionistRoutineRepository.findAllByNutritionistId(idNutritionist);
        return generateRoutineResponse(routineSuggestions);
    }

    public List<GetNutritionistRoutineDTO> getRoutineSuggestionByRecommendationId(UUID idRecommendation){
        List<NutritionistRoutineModel> routineSuggestions = nutritionistRoutineRepository.findByRecommendationId(idRecommendation);
        return generateRoutineResponse(routineSuggestions);
    }

    private List<GetNutritionistRoutineDTO> generateRoutineResponse(List<NutritionistRoutineModel> routines) {
        List<GetNutritionistRoutineDTO> responseToReturn = new ArrayList<>();

        for (var routine : routines) {
            List<GetPatientMealsRoutineDTO> getMealsRoutineDTOList = new ArrayList<>();

            var weekRepetitions = new WeekRepetitionsDTO();

            weekRepetitions.setMonday(routine.isMonday());
            weekRepetitions.setTuesday(routine.isTuesday());
            weekRepetitions.setWednesday(routine.isWednesday());
            weekRepetitions.setThursday(routine.isThursday());
            weekRepetitions.setFriday(routine.isFriday());
            weekRepetitions.setSaturday(routine.isSaturday());
            weekRepetitions.setSunday(routine.isSunday());

            List<NutritionistRoutineMealModel> mealsReferencyRoutine = nutritionistRoutineMealRepository.findAllByRoutineId(routine.getId());

            double totalCalories = 0.0;

            for (NutritionistRoutineMealModel routineMealCurrent : mealsReferencyRoutine) {
                totalCalories += routineMealCurrent.getMeal().getTotalCalories();
            }

            for (NutritionistRoutineMealModel mealToAdd : mealsReferencyRoutine) {
                getMealsRoutineDTOList.add(GetPatientMealsRoutineDTO.builder()
                    .id(mealToAdd.getId())
                    .mealId(mealToAdd.getMeal().getId())
                    .notifyAt(mealToAdd.getNotifyAt())
                    .photo(mealToAdd.getMeal().getPhoto())
                    .name(mealToAdd.getMeal().getName())
                    .description(mealToAdd.getMeal().getDescription())
                    .category(mealToAdd.getMeal().getCategory())
                    .totalCalories(mealToAdd.getMeal().getTotalCalories())
                    .build());
            }

            GetNutritionistRoutineDTO routineToBeReturned = new GetNutritionistRoutineDTO();

            routineToBeReturned.setId(routine.getId());
            routineToBeReturned.setOriginalRoutineId(routine.getOriginalRoutineId());
            routineToBeReturned.setIdNutritionist(routine.getNutritionistModel().getId());
            routineToBeReturned.setIdPatient(routine.getNutritionistModel().getId());
            routineToBeReturned.setIdRecommendation(routine.getRecommendationModel().getId());
            routineToBeReturned.setName(routine.getName());
            routineToBeReturned.setInUsage(routine.isInUsage());
            routineToBeReturned.setMeals(getMealsRoutineDTOList);
            routineToBeReturned.setWeekRepetitions(weekRepetitions);
            routineToBeReturned.setTotalCaloriesInTheDay(totalCalories);
            routineToBeReturned.setCreatedAt(routine.getCreatedAt());
            routineToBeReturned.setUpdatedAt(routine.getUpdatedAt());

            responseToReturn.add(routineToBeReturned);
        }

        return responseToReturn;
    }

    // Utils
    private static List<GetPatientMealsRoutineDTO> getMealsRoutineDTO(List<NutritionistRoutineMealModel> createdRoutineMeals) {

        List<GetPatientMealsRoutineDTO> getMealsRoutineDTOS = new ArrayList<>();

        for(var routineMeal  : createdRoutineMeals) {
            GetPatientMealsRoutineDTO mealRoutine = new GetPatientMealsRoutineDTO();

            mealRoutine.setId(routineMeal.getId());
            mealRoutine.setMealId(routineMeal.getMeal().getId());
            mealRoutine.setName(routineMeal.getMeal().getName());
            mealRoutine.setDescription(routineMeal.getMeal().getDescription());
            mealRoutine.setCategory(routineMeal.getMeal().getCategory());
            mealRoutine.setTotalCalories(routineMeal.getMeal().getTotalCalories());
            mealRoutine.setNotifyAt(routineMeal.getNotifyAt());
            mealRoutine.setPhoto(routineMeal.getMeal().getPhoto());

            getMealsRoutineDTOS.add(mealRoutine);
        }

        return getMealsRoutineDTOS;
    }

}
