package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.others.AnalysisDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

import java.util.UUID;

public interface IAnalysisService {
    AnalysisDTO getAnalysis(UUID patientId) throws ResourceNotFoundException;
}
