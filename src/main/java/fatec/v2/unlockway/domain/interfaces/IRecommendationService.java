package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.recommendation.SaveRecommendationDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.api.dto.recommendation.AcceptRecommendationDTO;
import fatec.v2.unlockway.api.dto.recommendation.GetRecommendationDTO;

import java.util.List;
import java.util.UUID;


public interface IRecommendationService {
    List<GetRecommendationDTO> getAllRecommendations() throws ResourceNotFoundException;
    GetRecommendationDTO getRecommendationById(UUID id) throws ResourceNotFoundException;
    GetRecommendationDTO createRecommendation(SaveRecommendationDTO dto) throws ResourceNotFoundException;
    GetRecommendationDTO updateRecommendation(UUID id, SaveRecommendationDTO dto) throws ResourceNotFoundException;
    String acceptRecommendationById(UUID id, AcceptRecommendationDTO dto) throws ResourceNotFoundException;
    String denyRecommendationById(UUID id, AcceptRecommendationDTO dto) throws ResourceNotFoundException;
    void deleteRecommendation(UUID id) throws ResourceNotFoundException;
}

