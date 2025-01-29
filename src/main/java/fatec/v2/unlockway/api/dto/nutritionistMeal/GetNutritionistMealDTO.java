package fatec.v2.unlockway.api.dto.nutritionistMeal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.ingredients.GetIngredientMealDTO;
import fatec.v2.unlockway.domain.enums.MealCategory;
import lombok.Data;

@Data
public class GetNutritionistMealDTO {
    private UUID id;
    private UUID idRecommendation;
    private UUID idNutritionist;
    private UUID idPatient;
    // Meal fields
    private UUID originalMealId;
    private String name;
    private String photo;
    private String description;
    private List<GetIngredientMealDTO> ingredients;
    private MealCategory category;
    private String preparationMethod;
    private double totalCalories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
