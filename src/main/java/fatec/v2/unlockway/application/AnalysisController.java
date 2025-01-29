package fatec.v2.unlockway.application;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fatec.v2.unlockway.services.AnalysisService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/analysis")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping("")
    public ResponseEntity<?> getAnalysis(@RequestParam("patientId") UUID patientId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(analysisService.getAnalysis(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
