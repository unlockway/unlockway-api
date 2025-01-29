package fatec.v2.unlockway.application;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fatec.v2.unlockway.api.dto.others.GetNotificationsDTO;
import fatec.v2.unlockway.services.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/findByPatientId")
    public ResponseEntity<?> findNotificationsBypatientId(@RequestParam("id") UUID id){
        try {
            List<GetNotificationsDTO> notifications = notificationService.findAllNotificationsByUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable("id") UUID id){
        try {
            notificationService.markNotificationAsRead(id);
            return ResponseEntity.status(HttpStatus.OK).body("Read");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
