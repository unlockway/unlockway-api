package fatec.v2.unlockway.api.dto.patient;

import fatec.v2.unlockway.domain.enums.Biotype;
import fatec.v2.unlockway.domain.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SavePatientDTO {
    String firstname;
    String lastname;
    String email;
    String password;
    Double height;
    Double weight;
    PatientGoalsDTO goals;
    Biotype biotype;
    Sex sex;
    String deviceToken;
}
