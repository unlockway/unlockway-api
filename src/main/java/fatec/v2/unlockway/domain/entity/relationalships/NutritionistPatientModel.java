package fatec.v2.unlockway.domain.entity.relationalships;

import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Table(name = "tb_nutritionist_patients")
public class NutritionistPatientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id")
    private NutritionistModel nutritionistModel;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;
}
