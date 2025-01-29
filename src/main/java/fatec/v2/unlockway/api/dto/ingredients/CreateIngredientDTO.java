package fatec.v2.unlockway.api.dto.ingredients;

import fatec.v2.unlockway.domain.enums.Measure;
import lombok.Data;

@Data
public class CreateIngredientDTO {
    private String id;
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