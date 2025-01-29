package fatec.v2.unlockway.domain.entity.relationalships;

import java.util.UUID;

import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_goals")
public class GoalsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "goals")
    private PatientModel patientModel;

    @Column(name = "gain_muscular_mass")
    private boolean gainMuscularMass;
    @Column(name = "maintain_health")
    private boolean maintainHealth;
    @Column(name = "lose_weight")
    private boolean loseWeight;
}
