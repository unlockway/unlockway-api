package fatec.v2.unlockway.domain.entity.patient;


import fatec.v2.unlockway.domain.entity.UserModel;
import fatec.v2.unlockway.domain.entity.relationalships.GoalsModel;
import fatec.v2.unlockway.domain.enums.Biotype;
import fatec.v2.unlockway.domain.enums.Sex;
import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_patients")
public class PatientModel extends UserModel {

    private double height;

    private double weight;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "goals_id", referencedColumnName = "id", nullable = true)
    private GoalsModel goals;

    @Enumerated(EnumType.STRING)
    private Biotype biotype;

    public double generateImc(double weight, double height) {
        DecimalFormat decfor = new DecimalFormat("0.00");

        double imc = weight / (height * height);
        return Double.parseDouble(decfor.format(imc).replace(",", "."));
    }
}
