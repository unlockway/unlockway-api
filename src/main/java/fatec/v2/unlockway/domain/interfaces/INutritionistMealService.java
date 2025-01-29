package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.nutritionistMeal.GetNutritionistMealDTO;
import fatec.v2.unlockway.api.dto.nutritionistMeal.SaveNutritionistMealDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface INutritionistMealService {
    List<GetNutritionistMealDTO> getAllMealSuggestions();
    GetNutritionistMealDTO findById(UUID id) throws ResourceNotFoundException;
    GetNutritionistMealDTO createMealSuggestion(SaveNutritionistMealDTO dto, Optional<MultipartFile> photo) throws ResourceNotFoundException;
    GetNutritionistMealDTO updateMealSuggestion(UUID id, SaveNutritionistMealDTO dto, Optional<MultipartFile> photo) throws ResourceNotFoundException;
    void deleteMealSuggestion(UUID id) throws ResourceNotFoundException;
    List<GetNutritionistMealDTO> getMealSuggestionsByPatientId(UUID idPatient);
    List<GetNutritionistMealDTO> getMealSuggestionsByNutritionistId(UUID idNutritionist);
}

