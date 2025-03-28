package fatec.v2.unlockway.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import fatec.v2.unlockway.domain.enums.Measure;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "tb_ingredients")
public class IngredientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private double calories;
    private double proteins;
    private double water;
    private String minerals;
    private String vitamins;

    // @Column(columnDefinition = "ENUM('AMOUNT', 'GRAMS', 'MILILITERS')")
    @Enumerated(EnumType.STRING)
    private Measure measure;
    private double fats;
    private String photo;
}
