package fatec.v2.unlockway.api.dto.recommendation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.nutritionistMeal.GetNutritionistMealDTO;
import fatec.v2.unlockway.api.dto.nutritionistRoutine.GetNutritionistRoutineDTO;
import lombok.Data;

@Data
public class GetRecommendationDTO {
    private UUID id;
    private UUID idNutritionist;
    private String nutritionistPhoto;
    private UUID idPatient;
    private String patientPhoto;
    private String description;
    private String patientComment;
    private String status;
    private List<GetNutritionistMealDTO> mealsSuggetions;
    private List<GetNutritionistRoutineDTO> routineSuggetions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
