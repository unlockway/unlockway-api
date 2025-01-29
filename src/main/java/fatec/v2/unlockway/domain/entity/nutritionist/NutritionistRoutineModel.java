package fatec.v2.unlockway.domain.entity.nutritionist;

import com.google.firebase.database.annotations.Nullable;
import fatec.v2.unlockway.domain.entity.RecommendationModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.relationalships.NutritionistRoutineMealModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "tb_nutritionist_routines")
public class NutritionistRoutineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id")
    private NutritionistModel nutritionistModel;

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private RecommendationModel recommendationModel;

    @Nullable
    @Column(name = "original_routine_id")
    private UUID originalRoutineId;

    @Column(name = "in_usage")
    private boolean inUsage;
    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "routine")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<NutritionistRoutineMealModel> routineMeal;
}
