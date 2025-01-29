package fatec.v2.unlockway.application.nutritionist;

import fatec.v2.unlockway.api.dto.nutritionistMeal.GetNutritionistMealDTO;
import fatec.v2.unlockway.api.dto.nutritionistMeal.SaveNutritionistMealDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.services.nutritionist.NutritionistMealService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/meal-suggestions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class NutritionistMealController {

    private final NutritionistMealService mealSuggestionService;

    @GetMapping
    public ResponseEntity<List<GetNutritionistMealDTO>> getAllMealSuggestions() {
        List<GetNutritionistMealDTO> mealSuggestions = mealSuggestionService.getAllMealSuggestions();
        return ResponseEntity.ok(mealSuggestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNutritionistMealDTO> getMealSuggestionById(@PathVariable UUID id) throws ResourceNotFoundException {
        GetNutritionistMealDTO mealSuggestion = mealSuggestionService.findById(id);
        return ResponseEntity.ok(mealSuggestion);
    }

    @PostMapping
    public ResponseEntity<?> createNutritionistMeal(@RequestParam("payload") String createNutritionistMealDTOAsJson, @RequestParam(value = "photo", required = false) Optional<MultipartFile> photo) throws ResourceNotFoundException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SaveNutritionistMealDTO saveNutritionistMealDTO = objectMapper.readValue(createNutritionistMealDTOAsJson, SaveNutritionistMealDTO.class);

            GetNutritionistMealDTO newMealSuggestion = mealSuggestionService.createMealSuggestion(saveNutritionistMealDTO, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMealSuggestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMealSuggestion(
        @PathVariable UUID id,
        @RequestParam("payload") String createNutritionistMealDTOAsJson, 
        @RequestParam(value = "photo", required = false) Optional<MultipartFile> photo) 
        throws ResourceNotFoundException 
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SaveNutritionistMealDTO saveNutritionistMealDTO = objectMapper.readValue(createNutritionistMealDTOAsJson, SaveNutritionistMealDTO.class);

            GetNutritionistMealDTO updatedMealSuggestion = mealSuggestionService.updateMealSuggestion(id, saveNutritionistMealDTO, photo);
            return ResponseEntity.ok(updatedMealSuggestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealSuggestion(@PathVariable UUID id) throws ResourceNotFoundException {
        mealSuggestionService.deleteMealSuggestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<GetNutritionistMealDTO>> getMealSuggestionsByPatientId(@PathVariable UUID idPatient) {
        List<GetNutritionistMealDTO> mealSuggestions = mealSuggestionService.getMealSuggestionsByPatientId(idPatient);
        return ResponseEntity.ok(mealSuggestions);
    }

    @GetMapping("/nutritionist/{idNutritionist}")
    public ResponseEntity<List<GetNutritionistMealDTO>> getMealSuggestionsByNutritionistId(@PathVariable UUID idNutritionist) {
        List<GetNutritionistMealDTO> mealSuggestions = mealSuggestionService.getMealSuggestionsByNutritionistId(idNutritionist);
        return ResponseEntity.ok(mealSuggestions);
    }
}

