package fatec.v2.unlockway.domain.entity.relationalships;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

import fatec.v2.unlockway.domain.entity.patient.PatientMealModel;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_patient_routine_meals")
public class PatientRoutineMealModel implements Serializable {

	@Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "meal_id")
	private PatientMealModel meal;
	
	@ManyToOne
	@JoinColumn(name = "routine_id")
	private PatientRoutineModel routine;

	private LocalTime notifyAt;
}
