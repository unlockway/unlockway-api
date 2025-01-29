package fatec.v2.unlockway.api.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import fatec.v2.unlockway.domain.enums.Biotype;
import fatec.v2.unlockway.domain.enums.Sex;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationPatientResponseDTO {
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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    private String token;
}
