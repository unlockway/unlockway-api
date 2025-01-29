package fatec.v2.unlockway.domain.repositories.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fatec.v2.unlockway.domain.entity.patient.PatientModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_patients WHERE email = :email")
    Optional<PatientModel> findByEmail(String email);
}
