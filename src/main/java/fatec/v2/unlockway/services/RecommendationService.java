package fatec.v2.unlockway.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fatec.v2.unlockway.api.dto.recommendation.AcceptRecommendationDTO;
import fatec.v2.unlockway.api.dto.recommendation.GetRecommendationDTO;
import fatec.v2.unlockway.api.dto.recommendation.SaveRecommendationDTO;
import fatec.v2.unlockway.domain.entity.RecommendationModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistMealModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistRoutineModel;
import fatec.v2.unlockway.domain.entity.patient.PatientMealModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;
import fatec.v2.unlockway.domain.entity.relationalships.NutritionistRoutineMealModel;
import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;
import fatec.v2.unlockway.domain.enums.Status;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IRecommendationService;
import fatec.v2.unlockway.domain.repositories.RecommendationRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistMealRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRoutineMealRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRoutineRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientMealRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineMealRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineRepository;
import fatec.v2.unlockway.services.nutritionist.NutritionistMealService;
import fatec.v2.unlockway.services.nutritionist.NutritionistRoutineService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService implements IRecommendationService {

    private final RecommendationRepository recommendationRepository;
    
    private final PatientRepository patientRepository;
    private final PatientMealRepository patientMealRepository;
    private final PatientRoutineRepository patientRoutineRepository;

    private final NutritionistMealService nutritionistMealService;
    private final NutritionistRepository nutritionistRepository;
    private final NutritionistRoutineService nutritionistRoutineService;
    private final NutritionistMealRepository nutritionistMealRepository;
    private final NutritionistRoutineRepository nutritionistRoutineRepository;

    private final PatientRoutineMealRepository patientRoutineMealRepository;
    private final NutritionistRoutineMealRepository nutritionistRoutineMealRepository;

    public String acceptRecommendationById(UUID id, AcceptRecommendationDTO dto) throws ResourceNotFoundException {
        RecommendationModel recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendação não encontrada"));

        if(recommendation.getStatus() == Status.APPROVED) {
            throw new ResourceNotFoundException("Recomendação já foi aceita");
        }
        
        recommendation.setStatus(Status.APPROVED);
        recommendation.setPatientComment(dto.getPatientComment());

        // Nutritionist meals to patient meals.

        List<NutritionistMealModel> nutritionistMeals = nutritionistMealRepository.findByRecommendationId(id);

        PatientMealModel patientMeal;
        List<PatientMealModel> patientMeals = new ArrayList<>();

        if(nutritionistMeals.size() > 0) {
            try {
                for (NutritionistMealModel nutritionistMeal : nutritionistMeals) {
                    if (nutritionistMeal.getOriginalMealId() != null) {
                        patientMeal = patientMealRepository.findById(nutritionistMeal.getOriginalMealId())
                                .orElseThrow(() -> new ResourceNotFoundException("Refeição do paciente não encontrada"));
                    } else {
                        patientMeal = new PatientMealModel();
                    }

                    patientMeal.setPatientModel(recommendation.getPatientModel());
                    patientMeal.setName(nutritionistMeal.getName());
                    patientMeal.setDescription(nutritionistMeal.getDescription());
                    if(nutritionistMeal.getPhoto() != null) {
                        patientMeal.setPhoto(nutritionistMeal.getPhoto());
                    }
                    patientMeal.setCategory(nutritionistMeal.getCategory());
                    patientMeal.setPreparationMethod(nutritionistMeal.getPreparationMethod());
                    patientMeal.setTotalCalories(nutritionistMeal.getTotalCalories());

                    patientMeal.setCreatedAt(LocalDateTime.now());
                    patientMeal.setUpdatedAt(LocalDateTime.now());

                    PatientMealModel createdPatientMealModel = patientMealRepository.save(patientMeal);
                    patientMeals.add(createdPatientMealModel);
                }
            } catch (ResourceNotFoundException e) {
                throw e;
            }
        }
        
        // Nutritionist routines to patient routines.
        List<NutritionistRoutineModel> nutritionistRoutines = nutritionistRoutineRepository.findByRecommendationId(id);

        if (nutritionistRoutines.size() > 0) {
            try {
                for (NutritionistRoutineModel nutritionistRoutine : nutritionistRoutines) {
                    PatientRoutineModel patientRoutine = new PatientRoutineModel();

                    if (nutritionistRoutine.getOriginalRoutineId() != null) {
                        patientRoutine = patientRoutineRepository.findById(nutritionistRoutine.getOriginalRoutineId())
                                .orElseThrow(() -> new ResourceNotFoundException("Rotina do paciente não encontrada"));
                    }

                    patientRoutine.setPatientModel(recommendation.getPatientModel());
                    patientRoutine.setName(nutritionistRoutine.getName());
                    
                    patientRoutine.setSunday(nutritionistRoutine.isSunday());
                    patientRoutine.setMonday(nutritionistRoutine.isMonday());
                    patientRoutine.setTuesday(nutritionistRoutine.isTuesday());
                    patientRoutine.setWednesday(nutritionistRoutine.isWednesday());
                    patientRoutine.setThursday(nutritionistRoutine.isThursday());
                    patientRoutine.setFriday(nutritionistRoutine.isFriday());
                    patientRoutine.setSaturday(nutritionistRoutine.isSaturday());

                    patientRoutine.setInUsage(false);
                    
                    patientRoutine.setCreatedAt(LocalDateTime.now());
                    patientRoutine.setUpdatedAt(LocalDateTime.now());

                    // Routine Meals
                    List<NutritionistRoutineMealModel> nutritionistRoutineMeals = nutritionistRoutineMealRepository.findAllByRoutineId(nutritionistRoutine.getId());
                    List<PatientRoutineMealModel> patientRoutineMealModelList = new ArrayList<>();

                    for(NutritionistRoutineMealModel item : nutritionistRoutineMeals){
                        PatientRoutineMealModel routineMeal = new PatientRoutineMealModel();

                        routineMeal.setRoutine(patientRoutine);

                        routineMeal.setNotifyAt(item.getNotifyAt());
                        
                        // A pura gambiarra por falta de opção:
                        routineMeal.setMeal(patientMeals.stream()
                            .filter(x -> (x.getName() + x.getDescription() + x.getPreparationMethod() + x.getTotalCalories())
                               .equals(item.getMeal().getName() 
                                + item.getMeal().getDescription() 
                                + item.getMeal().getPreparationMethod() 
                                + item.getMeal().getTotalCalories()
                               ))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException(
                                "Refeição da rotina não encontrada"
                            ))
                        );

                        patientRoutineMealModelList.add(routineMeal);                        
                    }

                    patientRoutineRepository.save(patientRoutine);

                    patientRoutineMealRepository.saveAll(patientRoutineMealModelList);
                }
            } catch (ResourceNotFoundException e) {
                throw e;
            }
        }

        recommendationRepository.save(recommendation);
        
        return "Recomendação aceita com sucesso!";
     }

    public String denyRecommendationById(UUID id, AcceptRecommendationDTO dto) throws ResourceNotFoundException {
        RecommendationModel recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendação não encontrada"));
        
        recommendation.setStatus(Status.DENIED);
        recommendation.setPatientComment(dto.getPatientComment());
        recommendation.setUpdatedAt(LocalDateTime.now());

        recommendationRepository.save(recommendation);
        
        return "Recomendação negada com sucesso!";
    }

    public List<GetRecommendationDTO> getAllRecommendations() {
        return recommendationRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public GetRecommendationDTO getRecommendationById(UUID id) throws ResourceNotFoundException {
        RecommendationModel recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendação não encontrada"));
        return convertToResponseDTO(recommendation);
    }

    @Transactional
    public GetRecommendationDTO createRecommendation(SaveRecommendationDTO dto) throws ResourceNotFoundException {
        RecommendationModel recommendation = convertToEntity(dto);
        recommendation.setCreatedAt(LocalDateTime.now());
        recommendation.setUpdatedAt(LocalDateTime.now());

        RecommendationModel savedRecommendation = recommendationRepository.save(recommendation);
        return convertToResponseDTO(savedRecommendation);
    }

    @Transactional
    public GetRecommendationDTO updateRecommendation(UUID id, SaveRecommendationDTO dto) throws ResourceNotFoundException {
        RecommendationModel existingRecommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recomendação não encontrada!"));

        if(existingRecommendation.getStatus() == Status.APPROVED) {
            throw new ResourceNotFoundException("Recomendação já foi aceita");
        }
        
        if(existingRecommendation.getStatus() == Status.DENIED) {
            throw new ResourceNotFoundException("Recomendação já foi negada");
        }

        existingRecommendation.setDescription(dto.getDescription());
        existingRecommendation.setPatientComment(dto.getPatientComment());
        existingRecommendation.setStatus(Status.valueOf(dto.getStatus()));

        NutritionistModel nutritionist = nutritionistRepository.findById(dto.getIdNutritionist())
                .orElseThrow(() -> new ResourceNotFoundException("Nutricionista não encontrado"));
        PatientModel patient = patientRepository.findById(dto.getIdPatient())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        existingRecommendation.setNutritionistModel(nutritionist);
        existingRecommendation.setPatientModel(patient);
        existingRecommendation.setUpdatedAt(LocalDateTime.now());

        RecommendationModel updatedRecommendation = recommendationRepository.save(existingRecommendation);
        return convertToResponseDTO(updatedRecommendation);
    }

    public void deleteRecommendation(UUID id) throws ResourceNotFoundException {
        RecommendationModel recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
        recommendationRepository.delete(recommendation);
    }

    public List<GetRecommendationDTO> getRecommendationsByPatientId(UUID idPatient) {
        List<RecommendationModel> recommendations = recommendationRepository.findByPatientModelId(idPatient);
        return recommendations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<GetRecommendationDTO> getRecommendationsByNutritionistId(UUID idNutritionist) {
        List<RecommendationModel> recommendations = recommendationRepository.findByNutritionistModelId(idNutritionist);
        return recommendations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<GetRecommendationDTO> getRecommendationsByDescription(UUID id, String description){
         List<RecommendationModel> recommendations = recommendationRepository.findByDescription(id, description);
        return recommendations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private RecommendationModel convertToEntity(SaveRecommendationDTO dto) {
        RecommendationModel recommendation = new RecommendationModel();

        NutritionistModel nutritionist = nutritionistRepository.findById(dto.getIdNutritionist())
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado!"));
        PatientModel patient = patientRepository.findById(dto.getIdPatient())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        recommendation.setNutritionistModel(nutritionist);
        recommendation.setPatientModel(patient);
        recommendation.setDescription(dto.getDescription());
        recommendation.setPatientComment(""); // starts empty
        recommendation.setStatus(Status.valueOf(dto.getStatus()));
        return recommendation;
    }

    private GetRecommendationDTO convertToResponseDTO(RecommendationModel recommendation) {
        GetRecommendationDTO dto = new GetRecommendationDTO();

        dto.setId(recommendation.getId());
        dto.setIdNutritionist(recommendation.getNutritionistModel().getId());
        dto.setIdPatient(recommendation.getPatientModel().getId());
        dto.setDescription(recommendation.getDescription());
        dto.setPatientComment(recommendation.getPatientComment());
        dto.setStatus(recommendation.getStatus().name());

        dto.setNutritionistPhoto(recommendation.getNutritionistModel().getPhoto());
        dto.setPatientPhoto(recommendation.getPatientModel().getPhoto());

        // Set suggestions
        dto.setMealsSuggetions(nutritionistMealService.getMealSuggestionByRecommendationId(recommendation.getId()));
        dto.setRoutineSuggetions(nutritionistRoutineService.getRoutineSuggestionByRecommendationId(recommendation.getId()));
        dto.setCreatedAt(recommendation.getCreatedAt());
        dto.setUpdatedAt(recommendation.getUpdatedAt());
        return dto;
    }
}
