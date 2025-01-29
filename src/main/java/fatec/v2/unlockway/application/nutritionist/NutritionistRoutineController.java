package fatec.v2.unlockway.application.nutritionist;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RestController;

import fatec.v2.unlockway.api.dto.nutritionistRoutine.GetNutritionistRoutineDTO;
import fatec.v2.unlockway.api.dto.nutritionistRoutine.SaveNutritionistRoutineDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.services.nutritionist.NutritionistRoutineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/routine-suggestions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class NutritionistRoutineController {

    private final NutritionistRoutineService routineSuggestionService;

    @GetMapping
    public ResponseEntity<List<GetNutritionistRoutineDTO>> getAllRoutineSuggestions() {
        List<GetNutritionistRoutineDTO> routineSuggestions = routineSuggestionService.getAllRoutineSuggestions();
        return ResponseEntity.ok(routineSuggestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNutritionistRoutineDTO> getRoutineSuggestionById(@PathVariable UUID id) throws ResourceNotFoundException {
        GetNutritionistRoutineDTO routineSuggestion = routineSuggestionService.findById(id);
        return ResponseEntity.ok(routineSuggestion);
    }

    @PostMapping
    public ResponseEntity<?> createRoutineSuggestion(@RequestBody SaveNutritionistRoutineDTO dto) {
        try {
            GetNutritionistRoutineDTO newRoutineSuggestion = routineSuggestionService.createRoutineSuggestion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoutineSuggestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoutineSuggestion(
            @PathVariable UUID id,
            @RequestBody SaveNutritionistRoutineDTO dto) {
        try {
            routineSuggestionService.updateRoutineSuggestion(id, dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoutineSuggestion(@PathVariable UUID id) {
        try {
            routineSuggestionService.deleteRoutineSuggestion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<GetNutritionistRoutineDTO>> getRoutineSuggestionByPatientId(@PathVariable UUID idPatient) {
        List<GetNutritionistRoutineDTO> routineSuggestion = routineSuggestionService.getRoutineSuggestionByPatientId(idPatient);
        return ResponseEntity.ok(routineSuggestion);
    }

    @GetMapping("/nutritionist/{idNutritionist}")
    public ResponseEntity<List<GetNutritionistRoutineDTO>> getRoutineSuggestionByNutritionistId(@PathVariable UUID idNutritionist) {
        List<GetNutritionistRoutineDTO> routineSuggestion = routineSuggestionService.getRoutineSuggestionByNutritionistId(idNutritionist);
        return ResponseEntity.ok(routineSuggestion);
    }
}
