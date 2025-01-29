package fatec.v2.unlockway.domain.entity.patient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_patient_routines")
public class PatientRoutineModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientModel patientModel;

	@Column(name = "in_usage")
	private boolean inUsage;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "routine")	
	private List<PatientRoutineMealModel> routineMeal;
}
