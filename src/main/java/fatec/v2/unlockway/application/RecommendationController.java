package fatec.v2.unlockway.application;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fatec.v2.unlockway.api.dto.recommendation.AcceptRecommendationDTO;
import fatec.v2.unlockway.api.dto.recommendation.GetRecommendationDTO;
import fatec.v2.unlockway.api.dto.recommendation.SaveRecommendationDTO;
import fatec.v2.unlockway.services.RecommendationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/recommendations")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<GetRecommendationDTO>> getAllRecommendations() {
        List<GetRecommendationDTO> recommendations = recommendationService.getAllRecommendations();
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecommendationById(
            @PathVariable UUID id) {
        try {
            GetRecommendationDTO recommendation = recommendationService.getRecommendationById(id);
            return ResponseEntity.ok(recommendation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createRecommendation(
            @RequestBody SaveRecommendationDTO dto) {
        try {
            GetRecommendationDTO newRecommendation = recommendationService.createRecommendation(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRecommendation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecommendation(
            @PathVariable UUID id,
            @RequestBody SaveRecommendationDTO dto) {
        try {
            GetRecommendationDTO updatedRecommendation = recommendationService.updateRecommendation(id, dto);
            return ResponseEntity.ok(updatedRecommendation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecommendation(
            @PathVariable UUID id) {
        try {
            recommendationService.deleteRecommendation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<GetRecommendationDTO>> getRecommendationsByPatientId(
            @PathVariable UUID idPatient) {
        List<GetRecommendationDTO> recommendations = recommendationService.getRecommendationsByPatientId(idPatient);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetRecommendationDTO>> getRecommendationsByDescription(
            @RequestParam("patientId") UUID patientId,
            @RequestParam("description") String description) {
        List<GetRecommendationDTO> recommendations = recommendationService.getRecommendationsByDescription(patientId, description);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/nutritionist/{idNutritionist}")
    public ResponseEntity<List<GetRecommendationDTO>> getRecommendationsByNutritionistId(@PathVariable UUID idNutritionist) {
        List<GetRecommendationDTO> recommendations = recommendationService.getRecommendationsByNutritionistId(idNutritionist);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptRecommendationById(@PathVariable UUID id, @RequestBody AcceptRecommendationDTO dto) {
        try {
            var message = recommendationService.acceptRecommendationById(id, dto);
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deny/{id}")
    public ResponseEntity<?> denyRecommendationById(@PathVariable UUID id, @RequestBody AcceptRecommendationDTO dto) {
        try {
            var message = recommendationService.denyRecommendationById(id, dto);
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}