package fatec.v2.unlockway.domain.repositories.nutritionist;

import fatec.v2.unlockway.domain.entity.relationalships.NutritionistRoutineMealModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NutritionistRoutineMealRepository extends JpaRepository<NutritionistRoutineMealModel, UUID> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tb_nutritionist_routine_meals WHERE routine_id = :routineId AND meal_id = :mealId"
    )
    List<NutritionistRoutineMealModel> findAllByRoutineIdAndMealId(UUID routineId, UUID mealId);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tb_nutritionist_routine_meals WHERE routine_id = :routineId"
    )
    List<NutritionistRoutineMealModel> findAllByRoutineId(UUID routineId);
}
