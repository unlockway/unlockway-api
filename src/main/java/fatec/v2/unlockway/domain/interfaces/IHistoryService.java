package fatec.v2.unlockway.domain.interfaces;

import java.util.List;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.history.GetHistoryDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

public interface IHistoryService {
  List<GetHistoryDTO> findByPatientId(UUID patientId) throws ResourceNotFoundException;
  void toggleMealAsIngested(UUID routineId, UUID mealId) throws  ResourceNotFoundException;
}
