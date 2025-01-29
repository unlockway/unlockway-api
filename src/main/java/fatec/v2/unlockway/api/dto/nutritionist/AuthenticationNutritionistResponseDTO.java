package fatec.v2.unlockway.api.dto.nutritionist;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationNutritionistResponseDTO {
    UUID id;
    String firstname;
    String lastname;
    String photo;
    String email;
    private String cfn;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    private String token;
}
