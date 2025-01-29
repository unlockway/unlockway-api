package fatec.v2.unlockway.domain.repositories.nutritionist;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistRoutineModel;

public interface NutritionistRoutineRepository extends JpaRepository<NutritionistRoutineModel, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE patient_id = :idPatient")
    List<NutritionistRoutineModel> findAllByPatientId(UUID idPatient);
    
    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE patient_id = :idPatient AND nutritionist_id = :nutritionistId")
    List<NutritionistRoutineModel> findAllByPatientIdAndNutritionistId(UUID idPatient, UUID nutritionistId);

	@Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE nutritionist_id = :nutritionistId")
    List<NutritionistRoutineModel> findAllByNutritionistId(UUID nutritionistId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_nutritionist_routine_meals trm WHERE trm.routine_id = :routineId")
    void deleteAllRoutineMealsByRoutineId(UUID routineId);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE patient_id = :patientId AND nutritionist_id = :nutritionistId AND name ILIKE %:name%")
    List<NutritionistRoutineModel> findByName(UUID patientId, UUID nutritionistId, @Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE monday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfMonday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE tuesday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfTuesday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE wednesday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfWednesday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE thursday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfThursday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE friday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfFriday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE saturday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfSaturday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE sunday = 1 AND in_usage = 1")
    List<NutritionistRoutineModel> findAllOfSunday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_routines WHERE recommendation_id = :idRecommendation")
    List<NutritionistRoutineModel> findByRecommendationId(@Param("idRecommendation") UUID idRecommendation);
}
