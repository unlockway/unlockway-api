package fatec.v2.unlockway.application.nutritionist;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fatec.v2.unlockway.api.dto.nutritionist.GetNutritionistDTO;
import fatec.v2.unlockway.api.dto.nutritionist.GetNutritionistPatientDTO;
import fatec.v2.unlockway.api.dto.nutritionist.SaveNutritionistDTO;
import fatec.v2.unlockway.api.dto.patient.LinkPatientDTO;
import fatec.v2.unlockway.services.nutritionist.NutritionistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/nutritionist")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NutritionistController {
    private final ModelMapper modelMapper;
    private final NutritionistService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        try {
            GetNutritionistDTO user = service.findById(id);
            GetNutritionistDTO userDTO = modelMapper.map(user, GetNutritionistDTO.class);

            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody SaveNutritionistDTO user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.updateById(id, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<String> setUserProfilePhoto(@PathVariable UUID id, @RequestParam(value = "photo", required = true) MultipartFile profilePhoto) {
        try {
            var filename = service.setProfilePhoto(id, profilePhoto);
            return ResponseEntity.status(HttpStatus.OK).body(filename);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{id}/new-patient")
    public ResponseEntity<?> linkPatient(@PathVariable UUID id, @RequestBody LinkPatientDTO linkPatientDTO) {
        try {
            GetNutritionistPatientDTO linkedPatient = service.linkPatient(id, linkPatientDTO.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(linkedPatient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/remove-patient/{idRelation}")
    public ResponseEntity<?> unlinkPatients(@PathVariable UUID idRelation) {
        try {
            service.unlinkPatient(idRelation);
            return ResponseEntity.status(HttpStatus.OK).body("Paciente desvinculado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<?> getAllPatients(@PathVariable UUID id) {
        try {
            List<GetNutritionistPatientDTO> patients = service.getAllPatients(id);
            return ResponseEntity.status(HttpStatus.OK).body(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/patients/findByName")
    public ResponseEntity<?> findPatientsByName(@PathVariable UUID id, @RequestParam("name") String name) {
        try {
            List<GetNutritionistPatientDTO> patients = service.findPatientsByName(id, name);
            return ResponseEntity.status(HttpStatus.OK).body(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
