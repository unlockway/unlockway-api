package fatec.v2.unlockway.application;

import fatec.v2.unlockway.api.dto.nutritionist.AuthenticationNutritionistResponseDTO;
import fatec.v2.unlockway.api.dto.nutritionist.SaveNutritionistDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import fatec.v2.unlockway.api.dto.user.AuthenticateUserDTO;
import fatec.v2.unlockway.api.dto.patient.AuthenticationPatientResponseDTO;
import fatec.v2.unlockway.api.dto.patient.SavePatientDTO;
import fatec.v2.unlockway.services.nutritionist.NutritionistService;
import fatec.v2.unlockway.services.patient.PatientService;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final PatientService patientService;
    private final NutritionistService nutritionistService;

    @PostMapping("/register-patient")
    public ResponseEntity<?> registerPatient(@RequestBody SavePatientDTO payload) throws Exception {
        try {
            AuthenticationPatientResponseDTO user = patientService.register(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        }catch(Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/register-nutritionist")
    public ResponseEntity<?> registerNutritionist(@RequestBody SaveNutritionistDTO payload) throws Exception {
        try {
            AuthenticationNutritionistResponseDTO user = nutritionistService.register(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        }catch(Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateUserDTO credentials) throws Exception {
        try {
            var patient = patientService.authenticate(credentials);
            if(patient != null) return ResponseEntity.ok(patient);

            var nutritionist = nutritionistService.authenticate(credentials);
            if(nutritionist != null) return ResponseEntity.ok(nutritionist);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
