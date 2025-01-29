package fatec.v2.unlockway.application;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import fatec.v2.unlockway.api.dto.history.GetHistoryDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.services.HistoryService;

@RestController
@RequestMapping("/api/v2/history")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class HistoryController {

    private final HistoryService service;
    
    @GetMapping("/{idPatient}")
    public ResponseEntity<?> findByIdPatient(@PathVariable UUID idPatient){
        try {
            List<GetHistoryDTO> histories = service.findByPatientId(idPatient);

            return ResponseEntity.status(HttpStatus.OK).body(histories);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/ingested")
    public ResponseEntity<?> markRoutineMealAsIngested(@RequestParam("routine") UUID routineId, @RequestParam("meal") UUID mealId){
        try {
            service.toggleMealAsIngested(routineId, mealId);
            return ResponseEntity.status(HttpStatus.OK).body("Refeição Ingerida");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
