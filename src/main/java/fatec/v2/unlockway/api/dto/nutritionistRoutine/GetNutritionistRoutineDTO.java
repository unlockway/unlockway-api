package fatec.v2.unlockway.api.dto.nutritionistRoutine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.GetPatientMealsRoutineDTO;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistRoutineModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNutritionistRoutineDTO {
    private UUID id;
    private UUID idRecommendation;
    private UUID idNutritionist;
    private UUID idPatient;
    // Routine fields
    private UUID originalRoutineId;
    private String name;
    private boolean inUsage;
	private List<GetPatientMealsRoutineDTO> meals;
	private WeekRepetitionsDTO weekRepetitions;
	private double totalCaloriesInTheDay;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GetNutritionistRoutineDTO(Optional<NutritionistRoutineModel> routineUpdate) {
  }
}
