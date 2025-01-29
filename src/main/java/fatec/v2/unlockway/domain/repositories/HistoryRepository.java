package fatec.v2.unlockway.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fatec.v2.unlockway.domain.entity.HistoryModel;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository  extends JpaRepository<HistoryModel, UUID>  {
  @Query(nativeQuery = true, value = "SELECT * FROM tb_history WHERE patient_id = :patientid")
  List<HistoryModel> findByIDPatient(@Param("patientid") UUID patientId);

  @Query(nativeQuery = true, value = "SELECT * FROM tb_history WHERE routine_id = :routineid AND routine_meal_id = :routinemealid")
  List<HistoryModel> findByRoutineAndMeal(@Param("routineid") UUID routineId, @Param("routinemealid") UUID routineMealId);
}
