package fatec.v2.unlockway.domain.entity.nutritionist;

import fatec.v2.unlockway.domain.entity.RecommendationModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.relationalships.NutritionistRoutineMealModel;
import fatec.v2.unlockway.domain.enums.MealCategory;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name = "tb_nutritionist_meals")
public class NutritionistMealModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @jakarta.annotation.Nullable
    private String photo;

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
    @Column(name = "original_meal_id")
    private UUID originalMealId;

    @Enumerated(EnumType.STRING)

    // @Column(columnDefinition = "ENUM('BREAKFAST', 'LUNCH', 'SNACK', 'DINNER')")
    private MealCategory category;

    @Column(name = "preparation_method", columnDefinition = "TEXT")
    private String preparationMethod;

    @Column(name = "total_calories")
    private double totalCalories;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "meal")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<NutritionistRoutineMealModel> routineMeal;
}
