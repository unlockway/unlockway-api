package fatec.v2.unlockway.api.dto.patientRoutine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPatientRoutineDTO {
	private UUID id;
	private String name;
	private boolean inUsage;
	private List<GetPatientMealsRoutineDTO> meals;
	private WeekRepetitionsDTO weekRepetitions;
	private double totalCaloriesInTheDay;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

  public GetPatientRoutineDTO(Optional<PatientRoutineModel> routineUpdate) {
  }
}
