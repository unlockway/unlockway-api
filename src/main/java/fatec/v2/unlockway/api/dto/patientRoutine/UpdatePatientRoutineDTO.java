package fatec.v2.unlockway.api.dto.patientRoutine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientRoutineDTO {
    private UUID id;
    private String name;
    private Boolean inUsage;
    private List<CreatePatientMealsRoutineDTO> meals;
    private WeekRepetitionsDTO weekRepetitions;
}
