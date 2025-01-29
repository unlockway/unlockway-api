package fatec.v2.unlockway.api.dto.patientRoutine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

import fatec.v2.unlockway.domain.enums.MealCategory;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPatientMealsRoutineDTO {
	private UUID id;
    private UUID mealId;
    private LocalTime notifyAt;
    private String photo;
    private String name;
    private String description;
    private MealCategory category;
    private double totalCalories;
}
