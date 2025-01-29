package fatec.v2.unlockway.domain.entity.nutritionist;

import fatec.v2.unlockway.domain.entity.UserModel;
import fatec.v2.unlockway.domain.entity.relationalships.NutritionistPatientModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_nutritionists")
public class NutritionistModel extends UserModel {
    private String cfn;

    @OneToMany(mappedBy = "nutritionistModel", cascade = CascadeType.ALL)
    private List<NutritionistPatientModel> patientModelList;
}
