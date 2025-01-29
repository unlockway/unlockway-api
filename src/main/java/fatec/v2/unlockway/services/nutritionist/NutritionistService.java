package fatec.v2.unlockway.services.nutritionist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fatec.v2.unlockway.api.dto.nutritionist.AuthenticationNutritionistResponseDTO;
import fatec.v2.unlockway.api.dto.nutritionist.GetNutritionistDTO;
import fatec.v2.unlockway.api.dto.nutritionist.GetNutritionistPatientDTO;
import fatec.v2.unlockway.api.dto.nutritionist.SaveNutritionistDTO;
import fatec.v2.unlockway.api.dto.patient.PatientGoalsDTO;
import fatec.v2.unlockway.api.dto.user.AuthenticateUserDTO;
import fatec.v2.unlockway.azure.services.BlobStorage;
import fatec.v2.unlockway.config.JwtService;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.relationalships.NutritionistPatientModel;
import fatec.v2.unlockway.domain.enums.Role;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.INutritionistService;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistPatientRepository;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NutritionistService implements INutritionistService {
    private final NutritionistRepository nutritionistRepository;
    private final PatientRepository patientRepository;
    private final NutritionistPatientRepository nutritionistPatientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthenticationNutritionistResponseDTO register(SaveNutritionistDTO nutritionist) throws Exception {
        NutritionistModel userToBeRegistered = new NutritionistModel();

        Optional<PatientModel> existingPatient = patientRepository.findByEmail(nutritionist.getEmail().toLowerCase());
        if(existingPatient.isPresent()) throw new Exception("Paciente com e-mail " + nutritionist.getEmail() + " já existe");

        Optional<NutritionistModel> existingNutritionist = nutritionistRepository.findByEmail(nutritionist.getEmail().toLowerCase());
        if(existingNutritionist.isPresent()) throw new Exception("Nutricionista com e-mail " + nutritionist.getEmail() + " já existe");

        if(nutritionist.getCfn().isEmpty()) throw new Exception("CFN inválido");

        if(nutritionistRepository.findByCfn(nutritionist.getCfn()).isPresent()) throw new Exception("CFN já cadastrado");

        if(nutritionist.getFirstname().isEmpty()) throw new Exception("Nome de usuário inválido");

        userToBeRegistered.setFirstname(nutritionist.getFirstname());
        userToBeRegistered.setLastname(nutritionist.getLastname());
        userToBeRegistered.setEmail(nutritionist.getEmail().toLowerCase());
        userToBeRegistered.setPassword(passwordEncoder.encode(nutritionist.getPassword()));
        userToBeRegistered.setCfn(nutritionist.getCfn());
        userToBeRegistered.setPatientModelList(new ArrayList<>());
        userToBeRegistered.setRole(Role.USER);
        userToBeRegistered.setDeviceToken(nutritionist.getDeviceToken());

        NutritionistModel createdUser = nutritionistRepository.save(userToBeRegistered);

        return generateTokenAndReturnUser(createdUser);
    }

    private AuthenticationNutritionistResponseDTO generateTokenAndReturnUser(NutritionistModel user) {
        var jwtToken = jwtService.generateToken(user);

        AuthenticationNutritionistResponseDTO response =  modelMapper.map(returnUserDTO(user), AuthenticationNutritionistResponseDTO.class);
        response.setToken(jwtToken);

        return response;
    }

    @Override
    public AuthenticationNutritionistResponseDTO authenticate(AuthenticateUserDTO credentials) throws Exception {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail().toLowerCase(), credentials.getPassword()));

        if(authentication.isAuthenticated()) {
            var user = nutritionistRepository.findByEmail(credentials.getEmail().toLowerCase()).orElse(null);
            if(user == null) return null;
            return generateTokenAndReturnUser(user);
        }else{
            throw new Exception("Usuário com e-mail ou senha inválida");
        }
    }

    @Override
    public AuthenticationNutritionistResponseDTO updateById(UUID id, SaveNutritionistDTO nutritionistDTO) throws ResourceNotFoundException {
        NutritionistModel existingUser = nutritionistRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado!"));

        if(nutritionistDTO.getEmail() != null)  existingUser.setEmail(nutritionistDTO.getEmail());
        if(nutritionistDTO.getPassword() != null) existingUser.setPassword(passwordEncoder.encode(nutritionistDTO.getPassword()));
        if(nutritionistDTO.getFirstname() != null) existingUser.setFirstname(nutritionistDTO.getFirstname());
        if(nutritionistDTO.getLastname() != null) existingUser.setLastname(nutritionistDTO.getLastname());
        if(nutritionistDTO.getCfn() != null) existingUser.setCfn(nutritionistDTO.getCfn());
        if(nutritionistDTO.getDeviceToken() != null) existingUser.setDeviceToken(nutritionistDTO.getDeviceToken());

        var updatedUser = nutritionistRepository.save(existingUser);

        return generateTokenAndReturnUser(updatedUser);
    }

    @Override
    public GetNutritionistDTO findById(UUID id) throws ResourceNotFoundException {
        var user = nutritionistRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado!"));

        return returnUserDTO(user);
    }

    private GetNutritionistDTO returnUserDTO(NutritionistModel nutritionistModel) {

        return GetNutritionistDTO.builder()
            .id(nutritionistModel.getId())
            .email(nutritionistModel.getEmail())
            .firstname(nutritionistModel.getFirstname())
            .lastname(nutritionistModel.getLastname())
            .photo(nutritionistModel.getPhoto())
            .cfn(nutritionistModel.getCfn())
            .createdAt(nutritionistModel.getCreatedAt())
            .updatedAt(nutritionistModel.getUpdatedAt())
            .build();
    }

    @Override
    @Transactional
    public String setProfilePhoto(UUID patientId, MultipartFile photo) throws ResourceNotFoundException {
        NutritionistModel nutritionist = nutritionistRepository.findById(patientId).orElseThrow(()->new ResourceNotFoundException("Usuário não existe e por tanto não pode ter uma foto salva"));
        String containerName = "users";

        deleteUserPhotoFromAzureBlobStorage(nutritionist, containerName);

        String filename = BlobStorage.storePhotoIntoAzureBlobStorage(photo, containerName);
        // TODO:Apply the right blob storage URL
        nutritionist.setPhoto("<Blob Storage https link here>" + containerName  + "/" + filename);

        nutritionistRepository.save(nutritionist);
        return nutritionist.getPhoto();
    }

    @Override
    public GetNutritionistPatientDTO linkPatient(UUID nutritionistId, String email) throws ResourceNotFoundException {

        PatientModel patient = patientRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("Paciente não encontrado!"));
        NutritionistModel nutritionist = nutritionistRepository.findById(nutritionistId).orElseThrow(()->new ResourceNotFoundException("Nutricionista não encontrado!"));

        if(nutritionistPatientRepository.findByNutritionistIdAndPatientId(patient.getId(), nutritionist.getId()).isPresent()) {
            throw new ResourceNotFoundException("Paciente já vinculado a este nutricionista!");
        }

        if(nutritionistPatientRepository.findByPatientId(patient.getId()).isPresent()) {
            throw new ResourceNotFoundException("Paciente já vinculado a um nutricionista!");
        }

        NutritionistPatientModel nutritionistPatientModel = new NutritionistPatientModel();

        nutritionistPatientModel.setPatientModel(patient);
        nutritionistPatientModel.setNutritionistModel(nutritionist);

        nutritionistPatientRepository.save(nutritionistPatientModel);

        PatientGoalsDTO goals = new PatientGoalsDTO();

        goals.setLoseWeight(patient.getGoals().isLoseWeight());
        goals.setGainMuscularMass(patient.getGoals().isGainMuscularMass());
        goals.setMaintainHealth(patient.getGoals().isMaintainHealth());

        return GetNutritionistPatientDTO.builder()
                .idRelation(nutritionistPatientModel.getId())
                .id(patient.getId())
                .firstname(patient.getFirstname())
                .lastname(patient.getLastname())
                .photo(patient.getPhoto())
                .email(patient.getEmail())
                .height(patient.getHeight())
                .weight(patient.getWeight())
                .imc(patient.generateImc(patient.getWeight(), patient.getHeight()))
                .goals(goals)
                .biotype(patient.getBiotype())
                .sex(patient.getSex())
                .build();
    }

    @Override
    public void unlinkPatient(UUID idRelation) throws ResourceNotFoundException {
        NutritionistPatientModel relation = nutritionistPatientRepository.findById(idRelation).orElseThrow(() -> new ResourceNotFoundException("Relacionamento não encontrado!"));
        nutritionistPatientRepository.delete(relation);
    }

    @Override
    public List<GetNutritionistPatientDTO> getAllPatients(UUID nutritionistId) throws ResourceNotFoundException {

        List<NutritionistPatientModel> relations = nutritionistPatientRepository.findByNutritionistId(nutritionistId);
        List<GetNutritionistPatientDTO> patients = new ArrayList<>();

        for (NutritionistPatientModel relation : relations) {
            PatientModel patient = relation.getPatientModel();
            PatientGoalsDTO goals = new PatientGoalsDTO();
            goals.setLoseWeight(patient.getGoals().isLoseWeight());
            goals.setGainMuscularMass(patient.getGoals().isGainMuscularMass());
            goals.setMaintainHealth(patient.getGoals().isMaintainHealth());

            GetNutritionistPatientDTO patientDTO = GetNutritionistPatientDTO.builder()
                    .idRelation(relation.getId())
                    .id(patient.getId())
                    .firstname(patient.getFirstname())
                    .lastname(patient.getLastname())
                    .photo(patient.getPhoto())
                    .email(patient.getEmail())
                    .height(patient.getHeight())
                    .weight(patient.getWeight())
                    .imc(patient.generateImc(patient.getWeight(), patient.getHeight()))
                    .goals(goals)
                    .biotype(patient.getBiotype())
                    .sex(patient.getSex())
                    .build();

            patients.add(patientDTO);
        }

        return patients;
    }

   @Override
public List<GetNutritionistPatientDTO> findPatientsByName(UUID nutritionistId, String name) throws ResourceNotFoundException {
    List<NutritionistPatientModel> relations = nutritionistPatientRepository.findByNutritionistId(nutritionistId);
    List<GetNutritionistPatientDTO> patients = new ArrayList<>();

    for (NutritionistPatientModel relation : relations) {
        PatientModel patient = relation.getPatientModel();
        if (patient.getFirstname().toLowerCase().contains(name.toLowerCase()) ||
            patient.getLastname().toLowerCase().contains(name.toLowerCase())) {
            PatientGoalsDTO goals = new PatientGoalsDTO();
            goals.setLoseWeight(patient.getGoals().isLoseWeight());
            goals.setGainMuscularMass(patient.getGoals().isGainMuscularMass());
            goals.setMaintainHealth(patient.getGoals().isMaintainHealth());

            GetNutritionistPatientDTO patientDTO = GetNutritionistPatientDTO.builder()
                    .idRelation(relation.getId())
                    .id(patient.getId())
                    .firstname(patient.getFirstname())
                    .lastname(patient.getLastname())
                    .photo(patient.getPhoto())
                    .email(patient.getEmail())
                    .height(patient.getHeight())
                    .weight(patient.getWeight())
                    .imc(patient.generateImc(patient.getWeight(), patient.getHeight()))
                    .goals(goals)
                    .biotype(patient.getBiotype())
                    .sex(patient.getSex())
                    .build();

            patients.add(patientDTO);
        }
    }

    return patients;
}


    private void deleteUserPhotoFromAzureBlobStorage(NutritionistModel nutritionistModel, String containerName) {
        var oldPhoto = nutritionistModel.getPhoto();

        if(oldPhoto != null) {
            // TODO:Apply the right blob storage URL
            oldPhoto = oldPhoto.substring(("<Blob Storage https link here>" + containerName + "/").length());
            BlobStorage.removePhotoFromAzureBlobStorage(oldPhoto, containerName);
        }
    }
}
