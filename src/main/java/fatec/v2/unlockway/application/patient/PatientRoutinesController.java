package fatec.v2.unlockway.application.patient;

import fatec.v2.unlockway.api.dto.patientRoutine.CreatePatientRoutineDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.GetPatientRoutineDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.UpdatePatientRoutineDTO;
import fatec.v2.unlockway.services.patient.PatientRoutinesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/routines")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class PatientRoutinesController {

    private final PatientRoutinesService routinesService;

    @GetMapping("/patientId")
    public ResponseEntity<?> findRoutinesById(@RequestParam("id") UUID id) {
        try {
            List<GetPatientRoutineDTO> response = routinesService.getRoutinesBypatientId(id);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findByName")
    public  ResponseEntity<?> findByName(@RequestParam("patientId") UUID patientId, @RequestParam("name") String name){
        try {
            List<GetPatientRoutineDTO> routines = routinesService.findByName(patientId, name);
            return ResponseEntity.status(HttpStatus.OK).body(routines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/inusage")
    public  ResponseEntity<?> findRoutineInUsage(@RequestParam("patientId") UUID patientId){
        try {
            GetPatientRoutineDTO routine = routinesService.getRoutineInUsageBypatientId(patientId);
            return ResponseEntity.status(HttpStatus.OK).body(routine);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<String> UpdateRoutine(@RequestBody UpdatePatientRoutineDTO updateDTO) {
        try {
            routinesService.updateRoutine(updateDTO);

            return ResponseEntity.status(HttpStatus.OK).body("Rotina atualizada!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/use")
    public ResponseEntity<String> UpdateRoutineInUsage(@RequestParam(name = "patientId") UUID patientId, @RequestParam("routine") UUID id) {
        try {
            routinesService.routineInUsage(patientId, id);

            return ResponseEntity.status(HttpStatus.OK).body("Nova rotina selecionada!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("")
    public ResponseEntity<?> CreateRoutine(@RequestBody CreatePatientRoutineDTO routineCreate) {
        try {
            GetPatientRoutineDTO response = routinesService.createRoutines(routineCreate);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable UUID id) {
        try {
            routinesService.deleteRoutine(id);

            return ResponseEntity.status(HttpStatus.OK).body("Rotina deletada");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
