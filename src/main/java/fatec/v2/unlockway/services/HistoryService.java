package fatec.v2.unlockway.services;

import fatec.v2.unlockway.api.dto.history.GetHistoryDTO;
import fatec.v2.unlockway.api.dto.history.meals.GetHistoryMealDTO;
import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.domain.entity.HistoryModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;
import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IHistoryService;
import fatec.v2.unlockway.domain.repositories.HistoryRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HistoryService implements IHistoryService{
    private final HistoryRepository repository;
    private final PatientRoutineRepository routineRepository;

    @Override
    public List<GetHistoryDTO> findByPatientId(UUID patientId) throws ResourceNotFoundException {
        List<HistoryModel> historyList  = repository.findByIDPatient(patientId);

        // Group by using HashMap

        HashMap<String, List<HistoryModel>> historiesPerDate = new HashMap<String, List<HistoryModel>>();

        for (var history : historyList) {

            String date =  history.getDate().toString();

            if(historiesPerDate.get(date) == null) {
                historiesPerDate.put(date, new ArrayList<>(List.of(history)));
            }else {
                historiesPerDate.get(date).add(history);
            }

        }

        List<GetHistoryDTO> historyDTOList = new ArrayList<>();

        // Converting each group into a history routine with meals

        historiesPerDate.forEach((date, histories) -> {

            GetHistoryDTO getHistoryDTO = new GetHistoryDTO();

            PatientRoutineModel routine = histories.get(0).getRoutine();

            WeekRepetitionsDTO weekRepetitionsDTO = new WeekRepetitionsDTO(
                routine.isMonday(),
                routine.isTuesday(),
                routine.isWednesday(),
                routine.isThursday(),
                routine.isFriday(),
                routine.isSaturday(),
                routine.isSunday()
            );

            getHistoryDTO.setIdRoutine(routine.getId());
            getHistoryDTO.setName(routine.getName());
            getHistoryDTO.setDate(histories.get(0).getDate());
            getHistoryDTO.setWeekRepetitions(weekRepetitionsDTO);

            List<GetHistoryMealDTO> getHistoryMealDTOS = new ArrayList<>();

            for(var history : histories) {
                GetHistoryMealDTO getHistoryMealDTO = new GetHistoryMealDTO();

                var routineMeals = history.getRoutine().getRoutineMeal();

                var routineMealsFiltered =  routineMeals.stream().filter((e)->e.getId().equals(history.getRoutineMealId())).toList();

                if(routineMealsFiltered.size() == 1) {
                    var routineMeal = routineMealsFiltered.get(0);

                    getHistoryMealDTO.setId(history.getId());
                    getHistoryMealDTO.setIdMeal(routineMeal.getMeal().getId());
                    getHistoryMealDTO.setIdRoutineMeal(routineMeal.getId());
                    getHistoryMealDTO.setName(routineMeal.getMeal().getName());
                    getHistoryMealDTO.setPhoto(routineMeal.getMeal().getPhoto());
                    getHistoryMealDTO.setDescription(routineMeal.getMeal().getDescription());
                    getHistoryMealDTO.setCategory(routineMeal.getMeal().getCategory());
                    getHistoryMealDTO.setTotalCalories(routineMeal.getMeal().getTotalCalories());
                    getHistoryMealDTO.setIngested(history.isIngested());

                    getHistoryMealDTOS.add(getHistoryMealDTO);
                }

            }

            double totalCaloriesInTheDay = getTotalCaloriesInTheDay(getHistoryMealDTOS);

            getHistoryDTO.setMeals(getHistoryMealDTOS);
            getHistoryDTO.setTotalCaloriesInTheDay(totalCaloriesInTheDay);

            historyDTOList.add(getHistoryDTO);

        });

        return historyDTOList;
    }

    @Override
    public void toggleMealAsIngested(UUID routineId, UUID routineMealId) throws ResourceNotFoundException {

        List<HistoryModel> existingHistory = repository.findByRoutineAndMeal(routineId, routineMealId);

        Instant utcNow = Instant.now();

        // Convert to Brazil time zone
        ZoneId brazilTimeZone = ZoneId.of("America/Sao_Paulo");
        LocalDate brazilTime = LocalDate.ofInstant(utcNow, brazilTimeZone);

        List<HistoryModel> existingHistoryFiltered = existingHistory.stream().filter((history)-> Objects.equals(history.getDate(), brazilTime)).toList();

        // Updating an existing one
        if(existingHistoryFiltered.size() == 1) {
            var history = existingHistoryFiltered.get(0);

            history.setIngested(!history.isIngested());

            repository.save(history);
        }else {
            // creating a new history to the specific meal.
            PatientRoutineModel routine = routineRepository.findById(routineId).orElseThrow(() -> new ResourceNotFoundException("Rotina não encontrada"));
            PatientModel patient = routine.getPatientModel();
            boolean ingested = true;

            HistoryModel history = HistoryModel.builder()
                    .routine(routine)
                    .patient(patient)
                    .routineMealId(routineMealId)
                    .ingested(ingested)
                    .date(brazilTime)
                    .build();

            repository.save(history);
        }
    }

    private static double getTotalCaloriesInTheDay(List<GetHistoryMealDTO> meals) {
        double totalCaloriesInTheDay = 0;

        for(var meal : meals) {
            if(meal.isIngested()) totalCaloriesInTheDay += meal.getTotalCalories();
        }

        return totalCaloriesInTheDay;
    }
}
