package fatec.v2.unlockway.api.dto.nutritionist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SaveNutritionistDTO {
    String firstname;
    String lastname;
    String photo;
    String email;
    String password;
    private String cfn;
    String deviceToken;
}
