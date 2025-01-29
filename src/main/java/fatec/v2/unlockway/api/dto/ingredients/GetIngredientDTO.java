package fatec.v2.unlockway.api.dto.ingredients;

import java.util.UUID;

import fatec.v2.unlockway.domain.enums.Measure;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetIngredientDTO {
    private UUID id;
    private String name;
    private String photo;
    private String description;
    private double calories;
    private double proteins;
    private double water;
    private String minerals;
    private String vitamins;
    private Measure measure;
    private double fats;
}
