package fatec.v2.unlockway.api.dto.patientRoutine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRoutineDTO {
    private String name;
    private Boolean inUsage;
    private List<CreatePatientMealsRoutineDTO> meals;
    private WeekRepetitionsDTO weekRepetitions;
}
