package fatec.v2.unlockway.application.patient;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import fatec.v2.unlockway.api.dto.patient.GetPatientDTO;
import fatec.v2.unlockway.api.dto.patient.SavePatientDTO;
import fatec.v2.unlockway.services.patient.PatientService;

@RestController
@RequestMapping("/api/v2/patient")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PatientController {

    private final ModelMapper modelMapper;
    private final PatientService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        try {
            GetPatientDTO user = service.findById(id);
            GetPatientDTO userDTO = modelMapper.map(user, GetPatientDTO.class);

            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());   
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody SavePatientDTO user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.updateById(id, user));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<String> setUserProfilePhoto(@PathVariable UUID id, @RequestParam(value = "photo", required = true) MultipartFile profilePhoto) {
        try {
            var filename = service.setProfilePhoto(id, profilePhoto);
            return ResponseEntity.status(HttpStatus.OK).body(filename);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
