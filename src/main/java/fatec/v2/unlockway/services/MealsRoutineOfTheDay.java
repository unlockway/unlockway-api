package fatec.v2.unlockway.services;

import fatec.v2.unlockway.domain.entity.MealsOfTheDay;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;
import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;
import fatec.v2.unlockway.domain.repositories.MealsOfTHeDayRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineMealRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MealsRoutineOfTheDay {

    private final PatientRoutineRepository routineRepository;
    private final MealsOfTHeDayRepository mealsOfTHeDayRepository;
    private final PatientRoutineMealRepository patientRoutineMealRepository;

    public List<PatientRoutineMealModel> getActualMealRoutines() {
        List<MealsOfTheDay> mealsOfTheDay = mealsOfTHeDayRepository.findAll();
        List<PatientRoutineMealModel> meals = new ArrayList<>();

        try {
            for (MealsOfTheDay mealOfTheDay : mealsOfTheDay) {
                Optional<PatientRoutineMealModel> routineMeal = patientRoutineMealRepository.findById(mealOfTheDay.getRoutineMealId());
                routineMeal.ifPresent(meals::add);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return meals;
    }

    public void addMeal(PatientRoutineMealModel meal) {
        mealsOfTHeDayRepository.save(MealsOfTheDay.builder().routineMealId(meal.getId()).build());
    }

    @Transactional
    public void removeMeal(UUID id) {
        mealsOfTHeDayRepository.deleteByRoutineMealId(id);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every midnight
    public void resetList() {
        // Get current time in UTC
        Instant utcNow = Instant.now();

        // Convert to Brazil time zone
        ZoneId brazilTimeZone = ZoneId.of("America/Sao_Paulo");
        LocalDate brazilTime = LocalDate.ofInstant(utcNow, brazilTimeZone);

       var routines = findAllOfTheDay(brazilTime);

       routines.forEach((routine)-> {
           mealsOfTHeDayRepository.saveAll(routine.getRoutineMeal().stream().map(e->MealsOfTheDay.builder().routineMealId(e.getId()).build()).toList());
       });
    }

    private List<PatientRoutineModel> findAllOfTheDay(LocalDate date) {
        var today = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        return switch (today) {
            case "Sunday" -> routineRepository.findAllOfSunday();
            case "Monday" -> routineRepository.findAllOfMonday();
            case "Tuesday" -> routineRepository.findAllOfTuesday();
            case "Wednesday" -> routineRepository.findAllOfWednesday();
            case "Thursday" -> routineRepository.findAllOfThursday();
            case "Friday" -> routineRepository.findAllOfFriday();
            default -> routineRepository.findAllOfSaturday();
        };

    }

}
