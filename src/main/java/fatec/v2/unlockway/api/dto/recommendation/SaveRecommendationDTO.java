package fatec.v2.unlockway.api.dto.recommendation;

import lombok.Data;

import java.util.UUID;

@Data
public class SaveRecommendationDTO {
    private UUID idNutritionist;
    private UUID idPatient;
    private String description;
    private String patientComment;
    private String status;
}
