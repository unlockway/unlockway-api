package fatec.v2.unlockway.domain.entity.patient;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;
import fatec.v2.unlockway.domain.enums.MealCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_patient_meals")
public class PatientMealModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Nullable
    private String photo;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;

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
    private List<PatientRoutineMealModel> routineMeal;
}
