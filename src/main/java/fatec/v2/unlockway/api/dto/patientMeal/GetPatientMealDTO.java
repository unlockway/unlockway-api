package fatec.v2.unlockway.api.dto.patientMeal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.api.dto.ingredients.GetIngredientMealDTO;
import fatec.v2.unlockway.domain.enums.MealCategory;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPatientMealDTO {
    private UUID id;
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