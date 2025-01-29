package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.patient.AuthenticationPatientResponseDTO;
import fatec.v2.unlockway.api.dto.patient.GetPatientDTO;
import fatec.v2.unlockway.api.dto.patient.SavePatientDTO;
import fatec.v2.unlockway.api.dto.user.AuthenticateUserDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IPatientService {
    AuthenticationPatientResponseDTO register(SavePatientDTO user) throws Exception;
    AuthenticationPatientResponseDTO updateById(UUID id, SavePatientDTO user) throws ResourceNotFoundException;
    GetPatientDTO findById(UUID id) throws ResourceNotFoundException;
    AuthenticationPatientResponseDTO authenticate(AuthenticateUserDTO credentials) throws Exception;
    String setProfilePhoto(UUID patientId, MultipartFile photo) throws ResourceNotFoundException;
}
