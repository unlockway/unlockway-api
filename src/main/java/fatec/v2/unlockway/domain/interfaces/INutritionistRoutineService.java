package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.nutritionistRoutine.GetNutritionistRoutineDTO;
import fatec.v2.unlockway.api.dto.nutritionistRoutine.SaveNutritionistRoutineDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface INutritionistRoutineService {

    List<GetNutritionistRoutineDTO> getAllRoutineSuggestions();
    List<GetNutritionistRoutineDTO> getRoutineSuggestionByNutritionistId(UUID id) throws ResourceNotFoundException;
    List<GetNutritionistRoutineDTO> getRoutineSuggestionByPatientId(UUID id) throws ResourceNotFoundException;
    List<GetNutritionistRoutineDTO> getRoutineSuggestionsByPatientIdAndNutritionistId(UUID patietnId, UUID nutritionistId) throws ResourceNotFoundException;
    List<GetNutritionistRoutineDTO> getRoutineSuggestionByRecommendationId(UUID id) throws ResourceNotFoundException;
    GetNutritionistRoutineDTO findById(UUID id) throws ResourceNotFoundException;
    GetNutritionistRoutineDTO createRoutineSuggestion(SaveNutritionistRoutineDTO dto) throws ResourceNotFoundException;
    void updateRoutineSuggestion(UUID id, SaveNutritionistRoutineDTO dto) throws ResourceNotFoundException;
    void deleteRoutineSuggestion(UUID id) throws ResourceNotFoundException;
}
