package fatec.v2.unlockway.api.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientGoalsDTO {
    private boolean gainMuscularMass;
    private boolean maintainHealth;
    private boolean loseWeight;
}
