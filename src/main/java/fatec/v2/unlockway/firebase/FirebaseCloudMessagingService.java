package fatec.v2.unlockway.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;
import fatec.v2.unlockway.api.dto.others.SendNotificationPayloadDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseCloudMessagingService {
    public void sendNotification(String deviceToken, SendNotificationPayloadDTO data) {
        try {
            Map<String, String> payload = new HashMap<>(); // Nowadays is empty, but we can put here everything we want.
            // TODO: The App Logo is a placeholder, we need to change it to the real logo.
            String placeholder = "<HTTPS URL Logo here (.png)>";

            Notification notification = Notification.builder()
                    .setTitle(data.getTitle())
                    .setBody(data.getBody())
                    .setImage(data.getImage() == null ? placeholder : data.getImage())
                    .build();

            Message message = Message.builder()
                    .setNotification(notification)
                    .putAllData(payload)
                    .setToken(deviceToken)
                    .build();

            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}