package fatec.v2.unlockway.notifications;

import fatec.v2.unlockway.api.dto.others.SendNotificationPayloadDTO;
import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
// import fatec.v2.unlockway.firebase.FirebaseCloudMessagingService;
import fatec.v2.unlockway.services.NotificationService;
import fatec.v2.unlockway.services.MealsRoutineOfTheDay;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class NotificationSchedulerService {

    private final MealsRoutineOfTheDay mealsRoutineOfTheDayService;
    // private final FirebaseCloudMessagingService firebaseCloudMessagingService;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 * * * * ?") // Run every minute;
    public void sendScheduledNotifications() {
        // Get current time in UTC
        Instant utcNow = Instant.now();

        // Convert to Brazil time zone
        ZoneId brazilTimeZone = ZoneId.of("America/Sao_Paulo");

        // Get the current time
        LocalTime currentBrazilTime = LocalTime.ofInstant(utcNow, brazilTimeZone);

        List<PatientRoutineMealModel> mealRoutines = mealsRoutineOfTheDayService.getActualMealRoutines();

        for (PatientRoutineMealModel meal : mealRoutines) {

            if (Duration.between(currentBrazilTime, meal.getNotifyAt()).toMinutes() < 1) {
                SendNotificationPayloadDTO notification = SendNotificationPayloadDTO.builder()
                    .title(meal.getMeal().getName())
                    .body(meal.getMeal().getDescription())
                    .image(meal.getMeal().getPhoto())
                    .build();

                // firebaseCloudMessagingService.sendNotification(meal.getMeal().getPatientModel().getDeviceToken(), notification);
                notificationService.saveNotification(meal.getMeal().getPatientModel(), notification);

                mealsRoutineOfTheDayService.removeMeal(meal.getId()); // Removing from the list after notify
            }
        }
    }
}
