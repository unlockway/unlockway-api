package fatec.v2.unlockway.application.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import fatec.v2.unlockway.api.dto.patientMeal.GetPatientMealDTO;
import fatec.v2.unlockway.api.dto.patientMeal.SavePatientMealDTO;
import fatec.v2.unlockway.domain.enums.MealCategory;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.services.patient.PatientMealService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/meals")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class PatientMealController {

    private final PatientMealService mealService;

    @GetMapping("/findById")
    public  ResponseEntity<?> findById(@RequestParam("id") UUID id){
        try {
            GetPatientMealDTO meal = mealService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(meal);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findByPatientId")
    public ResponseEntity<?> findBypatientId(@RequestParam("patientId") UUID patientId) {
        try {
            List<GetPatientMealDTO> meals = mealService.findBypatientId(patientId);
            return ResponseEntity.status(HttpStatus.OK).body(meals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findByCategory")
    public ResponseEntity<?> findByCategory(@RequestParam("patientId") UUID patientId, @RequestParam("category") MealCategory mealCategory) {
        try {
            List<GetPatientMealDTO> meals = mealService.findByCategory(patientId, mealCategory);
            return ResponseEntity.status(HttpStatus.OK).body(meals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam("patientId") UUID patientId, @RequestParam("name") String name) {
        try {
            List<GetPatientMealDTO> meals = mealService.findByName(patientId, name);
            return ResponseEntity.status(HttpStatus.OK).body(meals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createPatientMeal(@RequestParam("payload") String createMealDTOAsJson, @RequestParam(value = "photo", required = false) Optional<MultipartFile> photo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SavePatientMealDTO saveMealDTO = objectMapper.readValue(createMealDTOAsJson, SavePatientMealDTO.class);

            GetPatientMealDTO createdMeal = mealService.createMeal(saveMealDTO, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMeal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

    @PutMapping(value = "")
    public ResponseEntity<?> updateMeal(@RequestParam("payload") String updateMealDTOAsJson, @RequestParam("photo") Optional<MultipartFile> photo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SavePatientMealDTO saveMealDTO = objectMapper.readValue(updateMealDTOAsJson, SavePatientMealDTO.class);

            GetPatientMealDTO updatedMeal = mealService.updateMeal(saveMealDTO, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedMeal);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable UUID id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mealService.deleteMeal(id));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
