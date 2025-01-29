package fatec.v2.unlockway.domain.entity.relationalships;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistMealModel;
import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistRoutineModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_nutritionist_routine_meals")
public class NutritionistRoutineMealModel implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "meal_id")
	private NutritionistMealModel meal;
	
	@ManyToOne
	@JoinColumn(name = "routine_id")
	private NutritionistRoutineModel routine;

	private LocalTime notifyAt;
}
