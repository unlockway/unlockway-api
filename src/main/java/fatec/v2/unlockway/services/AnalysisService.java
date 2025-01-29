package fatec.v2.unlockway.services;

import fatec.v2.unlockway.api.dto.history.GetHistoryDTO;
import fatec.v2.unlockway.api.dto.others.AnalysisDTO;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IAnalysisService;
import fatec.v2.unlockway.services.patient.PatientMealService;
import fatec.v2.unlockway.services.patient.PatientRoutinesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalysisService implements IAnalysisService {

    private final PatientRoutinesService routinesService;
    private final PatientMealService mealService;
    private final HistoryService historyService;
    private final NotificationService notificationService;
    private final RecommendationService recommendationService;

    @Override
    public AnalysisDTO getAnalysis(UUID patientId) throws ResourceNotFoundException  {

        LocalDate startOfWeek = getStartOfWeek();

        var routines = routinesService.getRoutinesBypatientId(patientId);
        var meals = mealService.findBypatientId(patientId);
        var notifications = notificationService.findAllNotificationsByUser(patientId);
        var recommendations = recommendationService.getRecommendationsByPatientId(patientId);

        List<GetHistoryDTO> histories = historyService.findByPatientId(patientId);

        histories.sort(Comparator.comparing(GetHistoryDTO::getDate));

        List<Double> totalCaloriesOfWeek = Arrays.asList(new Double[7]);
        Collections.fill(totalCaloriesOfWeek, null);

        histories.forEach((history)-> {
            LocalDate historyDate = history.getDate();

            if (historyDate.isEqual(startOfWeek) || historyDate.isAfter(startOfWeek)) {
                var daysBetween = startOfWeek.datesUntil(historyDate).toList().size();
                totalCaloriesOfWeek.set(daysBetween, history.getTotalCaloriesInTheDay());
            }
        });

        return AnalysisDTO.builder()
                .routines(routines.size())
                .meals(meals.size())
                .notifications(notifications.size())
                .recommendations(recommendations.size())
                .weekCalories(totalCaloriesOfWeek)
                .build();
    }

    private static LocalDate getStartOfWeek() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        return LocalDate.ofInstant(startOfWeek.toInstant(), ZoneId.systemDefault());
    }
}
