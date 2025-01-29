package fatec.v2.unlockway.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import fatec.v2.unlockway.domain.entity.NotificationModel;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_notifications WHERE patient_id = :patientId")
    List<NotificationModel> findByPatientId(UUID patientId);
}
