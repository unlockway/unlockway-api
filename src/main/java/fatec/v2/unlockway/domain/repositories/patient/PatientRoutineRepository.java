package fatec.v2.unlockway.domain.repositories.patient;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;

@Repository
public interface PatientRoutineRepository extends JpaRepository<PatientRoutineModel, UUID> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE patient_id = :patientId")
    List<PatientRoutineModel> findAllByPatientId(UUID patientId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_patient_routine_meals trm WHERE trm.routine_id = :routineId")
    void deleteAllRoutineMealsByRoutineId(UUID routineId);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE patient_id = :patientId AND name ILIKE %:name%")
    List<PatientRoutineModel> findByName(UUID patientId, @Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE monday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfMonday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE tuesday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfTuesday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE wednesday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfWednesday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE thursday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfThursday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE friday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfFriday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE saturday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfSaturday();

    @Query(nativeQuery = true, value = "SELECT * FROM tb_patient_routines WHERE sunday = 1 AND in_usage = 1")
    List<PatientRoutineModel> findAllOfSunday();

}
