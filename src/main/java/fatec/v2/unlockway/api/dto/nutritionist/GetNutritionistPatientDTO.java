package fatec.v2.unlockway.api.dto.nutritionist;

import java.util.UUID;

import fatec.v2.unlockway.api.dto.patient.PatientGoalsDTO;
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
public class GetNutritionistPatientDTO {
    UUID idRelation;
    UUID id;
    String firstname;
    String lastname;
    String photo;
    String email;
    double height;
    double weight;
    double imc;
    PatientGoalsDTO goals;
    Biotype biotype;
    Sex sex;
}