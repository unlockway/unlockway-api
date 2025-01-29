package fatec.v2.unlockway.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistMealModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistRoutineModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_recommendations")
public class RecommendationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id")
    private NutritionistModel nutritionistModel;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;

    @OneToMany(mappedBy = "recommendationModel")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<NutritionistMealModel> meals;

    @OneToMany(mappedBy = "recommendationModel")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<NutritionistRoutineModel> routines;

    private String description;

    private String patientComment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
