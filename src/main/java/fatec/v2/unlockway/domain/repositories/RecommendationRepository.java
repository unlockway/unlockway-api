package fatec.v2.unlockway.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.v2.unlockway.domain.entity.RecommendationModel;

public interface RecommendationRepository extends JpaRepository<RecommendationModel, UUID> {
    List<RecommendationModel> findByPatientModelId(UUID idPatient);
    List<RecommendationModel> findByNutritionistModelId(UUID idNutritionist);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_recommendations WHERE patient_id = :patientId AND description ILIKE %:description%")
    List<RecommendationModel> findByDescription(UUID patientId, @Param("description") String name);
}
