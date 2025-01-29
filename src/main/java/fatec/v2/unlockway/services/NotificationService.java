package fatec.v2.unlockway.services;

import fatec.v2.unlockway.api.dto.others.GetNotificationsDTO;
import fatec.v2.unlockway.api.dto.others.SendNotificationPayloadDTO;
import fatec.v2.unlockway.domain.entity.NotificationModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.INotificationService;
import fatec.v2.unlockway.domain.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository repository;

    @Override
    public List<GetNotificationsDTO> findAllNotificationsByUser(UUID patientId) {
        List<NotificationModel> notifications = repository.findByPatientId(patientId);

        return notifications.stream().map((n)->{
            return GetNotificationsDTO.builder()
                .id(n.getId())
                .title(n.getTitle())
                .description(n.getDescription())
                .read(n.isRead())
                .date(n.getDate())
                .build();
        }).toList();
    }

    @Override
    public void saveNotification(PatientModel patient, SendNotificationPayloadDTO notificationDTO) {
        NotificationModel notification = NotificationModel.builder()
                .title(notificationDTO.getTitle())
                .description(notificationDTO.getBody())
                .read(false)
                .date(LocalDateTime.now())
                .patientModel(patient)
                .build();

        repository.save(notification);
    }

    @Override
    public void markNotificationAsRead(UUID id) throws ResourceNotFoundException {
        var notification = repository.findById(id).orElseThrow(()->new  ResourceNotFoundException("Notificação não existe"));
        notification.setRead(true);
        repository.save(notification);
    }

}
