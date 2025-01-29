package fatec.v2.unlockway.api.dto.history.meals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import fatec.v2.unlockway.domain.enums.MealCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetHistoryMealDTO {
    private UUID id;
    private UUID idMeal;
    private UUID idRoutineMeal;
    private boolean ingested;
    private String name;
    private String photo;
    private String description;
    private MealCategory category;
    private double totalCalories;
}
