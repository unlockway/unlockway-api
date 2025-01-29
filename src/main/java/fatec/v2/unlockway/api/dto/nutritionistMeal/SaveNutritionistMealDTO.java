package fatec.v2.unlockway.api.dto.nutritionistMeal;

import java.util.List;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.ingredients.CreateIngredientMealDTO;
import fatec.v2.unlockway.domain.enums.MealCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveNutritionistMealDTO {
    private UUID id;
    private UUID idRecommendation;

    // Meal fields
    private UUID originalMealId;
    private String name;
    private String description;
    private List<CreateIngredientMealDTO> ingredients;
    private MealCategory category;
    private String preparationMethod;
}
