package fatec.v2.unlockway.domain.interfaces;

import fatec.v2.unlockway.api.dto.others.GetNotificationsDTO;
import fatec.v2.unlockway.api.dto.others.SendNotificationPayloadDTO;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface INotificationService {

    List<GetNotificationsDTO> findAllNotificationsByUser(UUID patientId);
    void saveNotification(PatientModel patient, SendNotificationPayloadDTO notification);
    void markNotificationAsRead(UUID id) throws ResourceNotFoundException;
}
