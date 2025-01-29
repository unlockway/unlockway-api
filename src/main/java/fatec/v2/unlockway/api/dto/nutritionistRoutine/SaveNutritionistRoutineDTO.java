package fatec.v2.unlockway.api.dto.nutritionistRoutine;

import java.util.List;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.CreatePatientMealsRoutineDTO;
import lombok.Data;

@Data
public class SaveNutritionistRoutineDTO {
    private UUID idRecommendation;
    // Routine fields;
    private UUID originalRoutineId;
    private UUID id;
    private String name;
    private Boolean inUsage;
    private List<CreatePatientMealsRoutineDTO> meals;
    private WeekRepetitionsDTO weekRepetitions;
}
