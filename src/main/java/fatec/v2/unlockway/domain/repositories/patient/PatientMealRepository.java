package fatec.v2.unlockway.domain.repositories.patient;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fatec.v2.unlockway.domain.entity.patient.PatientMealModel;
import fatec.v2.unlockway.domain.enums.MealCategory;

@Repository
public interface PatientMealRepository extends JpaRepository<PatientMealModel, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_meals WHERE patient_id = :patientId")
    List<PatientMealModel> findByPatientModelId(UUID patientId);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_meals WHERE patient_id = :patientId AND category = :#{#category.name()}")
    List<PatientMealModel> findByCategory(UUID patientId, @Param("category") MealCategory mealCategory);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_meals WHERE patient_id = :patientId AND name ILIKE %:name%")
    List<PatientMealModel> findByName(UUID patientId, @Param("name") String name);
}
