package fatec.v2.unlockway.domain.interfaces;

import java.util.List;
import java.util.UUID;

import fatec.v2.unlockway.api.dto.patientRoutine.CreatePatientRoutineDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.GetPatientRoutineDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.UpdatePatientRoutineDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

public interface IPatientRoutinesService {
	List<GetPatientRoutineDTO> getRoutinesBypatientId(UUID id);
    List<GetPatientRoutineDTO> findByName(UUID patientId, String name);
    GetPatientRoutineDTO createRoutines(CreatePatientRoutineDTO routineCreate) throws ResourceNotFoundException;
    void updateRoutine(UpdatePatientRoutineDTO updateDTO) throws ResourceNotFoundException;
    void deleteRoutine(UUID id) throws ResourceNotFoundException;

    void routineInUsage(UUID patientId, UUID id) throws ResourceNotFoundException;
    GetPatientRoutineDTO getRoutineInUsageBypatientId(UUID patientId) throws ResourceNotFoundException;
}
