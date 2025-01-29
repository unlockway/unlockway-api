package fatec.v2.unlockway.domain.interfaces;

import org.springframework.web.multipart.MultipartFile;

import fatec.v2.unlockway.api.dto.patientMeal.GetPatientMealDTO;
import fatec.v2.unlockway.api.dto.patientMeal.SavePatientMealDTO;
import fatec.v2.unlockway.domain.enums.MealCategory;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPatientMealService {
    GetPatientMealDTO findById(UUID patientId)  throws ResourceNotFoundException;
    List<GetPatientMealDTO> findBypatientId(UUID patientId);
    List<GetPatientMealDTO> findByCategory(UUID patientId, MealCategory mealCategory);
    List<GetPatientMealDTO> findByName(UUID patientId, String name);
    GetPatientMealDTO createMeal(SavePatientMealDTO saveMealDTO, Optional<MultipartFile> photo) throws ResourceNotFoundException;
    GetPatientMealDTO updateMeal(SavePatientMealDTO saveMealDTO, Optional<MultipartFile> photo) throws ResourceNotFoundException;
    String deleteMeal(UUID id) throws ResourceNotFoundException;
}
