package fatec.v2.unlockway.domain.repositories.nutritionist;

import fatec.v2.unlockway.domain.entity.relationalships.NutritionistPatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NutritionistPatientRepository extends JpaRepository<NutritionistPatientModel, UUID> {
    @Query("SELECT np FROM NutritionistPatientModel np WHERE np.patientModel.id = :patient_id")
    Optional<NutritionistPatientModel> findByPatientId(UUID patient_id);

    @Query("SELECT np FROM NutritionistPatientModel np WHERE np.nutritionistModel.id = :nutritionist_id")
    List<NutritionistPatientModel> findByNutritionistId(UUID nutritionist_id);

    @Query("SELECT np FROM NutritionistPatientModel np WHERE np.nutritionistModel.id = :nutritionist_id AND np.patientModel.id = :patient_id")
    Optional<NutritionistPatientModel> findByNutritionistIdAndPatientId(UUID nutritionist_id, UUID patient_id);
}
