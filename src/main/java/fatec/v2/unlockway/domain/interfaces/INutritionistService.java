package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.nutritionist.AuthenticationNutritionistResponseDTO;
import fatec.v2.unlockway.api.dto.nutritionist.GetNutritionistDTO;
import fatec.v2.unlockway.api.dto.nutritionist.GetNutritionistPatientDTO;
import fatec.v2.unlockway.api.dto.nutritionist.SaveNutritionistDTO;
import fatec.v2.unlockway.api.dto.user.AuthenticateUserDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface INutritionistService {
    AuthenticationNutritionistResponseDTO register(SaveNutritionistDTO nutritionist) throws Exception;
    AuthenticationNutritionistResponseDTO updateById(UUID id, SaveNutritionistDTO nutritionist) throws ResourceNotFoundException;
    GetNutritionistDTO findById(UUID id) throws ResourceNotFoundException;
    AuthenticationNutritionistResponseDTO authenticate(AuthenticateUserDTO credentials) throws Exception;
    String setProfilePhoto(UUID nutritionistId, MultipartFile photo) throws ResourceNotFoundException;
    GetNutritionistPatientDTO linkPatient(UUID nutritionistId, String email) throws ResourceNotFoundException;
    void unlinkPatient(UUID idRelation) throws ResourceNotFoundException;
    List<GetNutritionistPatientDTO> getAllPatients(UUID nutritionistId) throws ResourceNotFoundException;
    List<GetNutritionistPatientDTO> findPatientsByName(UUID nutritionistId, String name) throws ResourceNotFoundException;
}
