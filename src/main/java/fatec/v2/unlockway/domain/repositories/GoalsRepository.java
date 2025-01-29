package fatec.v2.unlockway.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fatec.v2.unlockway.domain.entity.relationalships.GoalsModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalsRepository extends JpaRepository<GoalsModel, UUID>  {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_goals WHERE patient_id = :patientId")
    Optional<GoalsModel> findBypatientId(@Param("patientId") UUID patientId);

}
