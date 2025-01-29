package fatec.v2.unlockway.api.dto.patientMeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.api.dto.ingredients.CreateIngredientMealDTO;
import fatec.v2.unlockway.domain.enums.MealCategory;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavePatientMealDTO {
    private UUID patientId;
    private UUID id;
    private String name;
    private String description;
    private List<CreateIngredientMealDTO> ingredients;
    private MealCategory category;
    private String preparationMethod;
}
