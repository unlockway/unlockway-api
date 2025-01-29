package fatec.v2.unlockway.domain.repositories.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;

import java.util.List;
import java.util.UUID;

public interface PatientRoutineMealRepository extends JpaRepository<PatientRoutineMealModel, UUID> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tb_patient_routine_meals WHERE routine_id = :routineId AND meal_id = :mealId"
    )
    List<PatientRoutineMealModel> findAllByRoutineIdAndMealId(UUID routineId, UUID mealId);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tb_patient_routine_meals WHERE routine_id = :routineId"
    )
    List<PatientRoutineMealModel> findAllByRoutineId(UUID routineId);
}
