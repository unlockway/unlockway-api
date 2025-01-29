package fatec.v2.unlockway.services.patient;

import fatec.v2.unlockway.api.dto.patient.AuthenticationPatientResponseDTO;
import fatec.v2.unlockway.api.dto.patient.GetPatientDTO;
import fatec.v2.unlockway.api.dto.patient.PatientGoalsDTO;
import fatec.v2.unlockway.api.dto.patient.SavePatientDTO;
import fatec.v2.unlockway.api.dto.user.AuthenticateUserDTO;
import fatec.v2.unlockway.azure.services.BlobStorage;
import fatec.v2.unlockway.config.JwtService;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.relationalships.GoalsModel;
import fatec.v2.unlockway.domain.enums.Role;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IPatientService;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;
    private final NutritionistRepository nutritionistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthenticationPatientResponseDTO register(SavePatientDTO userDTO) throws Exception {
        PatientModel userToBeRegistered = new PatientModel();

        Optional<NutritionistModel> existingNutritionist = nutritionistRepository.findByEmail(userDTO.getEmail().toLowerCase());
        if(existingNutritionist.isPresent()) throw new Exception("Nutricionista com e-mail " + userDTO.getEmail() + " já existe");

        Optional<PatientModel> existingPatient = patientRepository.findByEmail(userDTO.getEmail().toLowerCase());
        if(existingPatient.isPresent()) throw new Exception("Paciente com e-mail " + userDTO.getEmail() + " já existe");

        if(userDTO.getFirstname().isEmpty()) throw new Exception("Nome de usuário inválido");

        userToBeRegistered.setFirstname(userDTO.getFirstname());
        userToBeRegistered.setLastname(userDTO.getLastname());
        userToBeRegistered.setEmail(userDTO.getEmail().toLowerCase());
        userToBeRegistered.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userToBeRegistered.setBiotype(userDTO.getBiotype());
        userToBeRegistered.setWeight(userDTO.getWeight());
        userToBeRegistered.setHeight(userDTO.getHeight());
        userToBeRegistered.setSex(userDTO.getSex());
        userToBeRegistered.setRole(Role.USER);
        userToBeRegistered.setDeviceToken(userDTO.getDeviceToken());

        // Goals
        GoalsModel userGoals = new GoalsModel();

        userGoals.setLoseWeight(userDTO.getGoals().isLoseWeight());
        userGoals.setMaintainHealth(userDTO.getGoals().isMaintainHealth());
        userGoals.setGainMuscularMass(userDTO.getGoals().isGainMuscularMass());

        userToBeRegistered.setGoals(userGoals);

        PatientModel createdUser = patientRepository.save(userToBeRegistered);

        return generateTokenAndReturnUser(createdUser);
    }

    private AuthenticationPatientResponseDTO generateTokenAndReturnUser(PatientModel user) {
        var jwtToken = jwtService.generateToken(user);

        PatientGoalsDTO goals = new PatientGoalsDTO();

        goals.setLoseWeight(user.getGoals().isLoseWeight());
        goals.setGainMuscularMass(user.getGoals().isGainMuscularMass());
        goals.setMaintainHealth(user.getGoals().isMaintainHealth());

        AuthenticationPatientResponseDTO response =  modelMapper.map(returnUserDTO(user), AuthenticationPatientResponseDTO.class);
        response.setToken(jwtToken);

        return response;
    }

    @Override
    public AuthenticationPatientResponseDTO authenticate(AuthenticateUserDTO credentials) throws Exception {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail().toLowerCase(), credentials.getPassword()));

        if(authentication.isAuthenticated()) {
            var user = patientRepository.findByEmail(credentials.getEmail().toLowerCase()).orElse(null);
            if(user == null) return null;
            return generateTokenAndReturnUser(user);
        }else{
            throw new Exception("Usuário com e-mail ou senha inválida");
        }
    }

    @Override
    public AuthenticationPatientResponseDTO updateById(UUID id, SavePatientDTO userDTO) throws ResourceNotFoundException {
        PatientModel existingUser = patientRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado!"));

        if(userDTO.getEmail() != null)  existingUser.setEmail(userDTO.getEmail());
        if(userDTO.getPassword() != null) existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if(userDTO.getFirstname() != null) existingUser.setFirstname(userDTO.getFirstname());
        if(userDTO.getLastname() != null) existingUser.setLastname(userDTO.getLastname());
        if(userDTO.getHeight() != null) existingUser.setHeight(userDTO.getHeight());
        if(userDTO.getWeight() !=  null) existingUser.setWeight(userDTO.getWeight());
        if(userDTO.getBiotype() != null) existingUser.setBiotype(userDTO.getBiotype());
        if(userDTO.getSex() != null) existingUser.setSex(userDTO.getSex());
        if(userDTO.getDeviceToken() != null) existingUser.setDeviceToken(userDTO.getDeviceToken());

        // Goals
        if(userDTO.getGoals() != null) {
            existingUser.getGoals().setLoseWeight(userDTO.getGoals().isLoseWeight());
            existingUser.getGoals().setMaintainHealth(userDTO.getGoals().isMaintainHealth());
            existingUser.getGoals().setGainMuscularMass(userDTO.getGoals().isGainMuscularMass());
        }

        var updatedUser = patientRepository.save(existingUser);

        return generateTokenAndReturnUser(updatedUser);
    }

    @Override
    public GetPatientDTO findById(UUID id) throws ResourceNotFoundException {
        var user = patientRepository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado!"));

        return returnUserDTO(user);
    }

    private GetPatientDTO returnUserDTO(PatientModel patientModel) {
        PatientGoalsDTO goals = new PatientGoalsDTO();

        goals.setLoseWeight(patientModel.getGoals().isLoseWeight());
        goals.setGainMuscularMass(patientModel.getGoals().isGainMuscularMass());
        goals.setMaintainHealth(patientModel.getGoals().isMaintainHealth());

        return GetPatientDTO.builder()
            .id(patientModel.getId())
            .email(patientModel.getEmail())
            .firstname(patientModel.getFirstname())
            .lastname(patientModel.getLastname())
            .photo(patientModel.getPhoto())
            .sex(patientModel.getSex())
            .height(patientModel.getHeight())
            .weight(patientModel.getWeight())
            .imc(patientModel.generateImc(patientModel.getWeight(), patientModel.getHeight()))
            .goals(goals)
            .biotype(patientModel.getBiotype())
            .createdAt(patientModel.getCreatedAt())
            .updatedAt(patientModel.getUpdatedAt())
            .build();
    }

    @Override
    @Transactional
    public String setProfilePhoto(UUID patientId, MultipartFile photo) throws ResourceNotFoundException {
        PatientModel user = patientRepository.findById(patientId).orElseThrow(()->new ResourceNotFoundException("Usuário não existe e por tanto não pode ter uma foto salva"));
        String containerName = "users";

        deleteUserPhotoFromAzureBlobStorage(user, containerName);

        String filename = BlobStorage.storePhotoIntoAzureBlobStorage(photo, containerName);
        // TODO:Apply the right blob storage URL
        user.setPhoto("<Blob Storage https link here>" + containerName  + "/" + filename);

        patientRepository.save(user);
        return user.getPhoto();
    }


    private void deleteUserPhotoFromAzureBlobStorage(PatientModel patientModel, String containerName) {
        var oldPhoto = patientModel.getPhoto();

        if(oldPhoto != null) {
            // TODO:Apply the right blob storage URL
            oldPhoto = oldPhoto.substring(("<Blob Storage https link here>" + containerName + "/").length());
            BlobStorage.removePhotoFromAzureBlobStorage(oldPhoto, containerName);
        }
    }
}
