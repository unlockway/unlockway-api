package fatec.v2.unlockway.api.dto.patientRoutine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientMealsRoutineDTO {
    private UUID idMeal;
    private LocalTime notifyAt;
}
