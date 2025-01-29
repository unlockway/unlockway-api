package fatec.v2.unlockway.services.patient;

import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.api.dto.patientRoutine.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import fatec.v2.unlockway.domain.exception.ResourceNotFoundException;
import fatec.v2.unlockway.domain.interfaces.IPatientRoutinesService;
import fatec.v2.unlockway.domain.entity.patient.PatientMealModel;
import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.entity.patient.PatientRoutineModel;
import fatec.v2.unlockway.domain.entity.relationalships.PatientRoutineMealModel;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineMealRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientMealRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRoutineRepository;
import fatec.v2.unlockway.services.MealsRoutineOfTheDay;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientRoutinesService implements IPatientRoutinesService {

    private final PatientRoutineMealRepository patientRoutineMealRepository;
    private final PatientMealRepository mealRepository;
    private final PatientRoutineRepository repository;
    private final MealsRoutineOfTheDay mealsRoutineOfTheDayService;

    @Override
    public List<GetPatientRoutineDTO> getRoutinesBypatientId(UUID id) {
        List<PatientRoutineModel> routines = repository.findAllByPatientId(id);
        return generateRoutineResponse(routines);
    }

    @Override
    public List<GetPatientRoutineDTO> findByName(UUID patientId, String name) {
        List<PatientRoutineModel> routines = repository.findByName(patientId, name.toLowerCase());
        return generateRoutineResponse(routines);
    }

    private List<GetPatientRoutineDTO> generateRoutineResponse(List<PatientRoutineModel> routines) {

        List<GetPatientRoutineDTO> responseToReturn = new ArrayList<>();

        for (var routine : routines) {
            List<GetPatientMealsRoutineDTO> getMealsRoutineDTOList = new ArrayList<>();

            var weekRepetitions = new WeekRepetitionsDTO();

            weekRepetitions.setMonday(routine.isMonday());
            weekRepetitions.setTuesday(routine.isTuesday());
            weekRepetitions.setWednesday(routine.isWednesday());
            weekRepetitions.setThursday(routine.isThursday());
            weekRepetitions.setFriday(routine.isFriday());
            weekRepetitions.setSaturday(routine.isSaturday());
            weekRepetitions.setSunday(routine.isSunday());

            List<PatientRoutineMealModel> mealsReferencyRoutine = patientRoutineMealRepository.findAllByRoutineId(routine.getId());

            double totalCalories = 0.0;

            for (PatientRoutineMealModel routineMealCurrent : mealsReferencyRoutine) {
                totalCalories += routineMealCurrent.getMeal().getTotalCalories();
            }

            for (PatientRoutineMealModel mealToAdd : mealsReferencyRoutine) {
                getMealsRoutineDTOList.add(GetPatientMealsRoutineDTO.builder()
                    .id(mealToAdd.getId())
                    .mealId(mealToAdd.getMeal().getId())
                    .notifyAt(mealToAdd.getNotifyAt())
                    .photo(mealToAdd.getMeal().getPhoto())
                    .name(mealToAdd.getMeal().getName())
                    .description(mealToAdd.getMeal().getDescription())
                    .category(mealToAdd.getMeal().getCategory())
                    .totalCalories(mealToAdd.getMeal().getTotalCalories())
                    .build());
            }

            GetPatientRoutineDTO routineToBeReturned = new GetPatientRoutineDTO();

            routineToBeReturned.setId(routine.getId());
            routineToBeReturned.setName(routine.getName());
            routineToBeReturned.setInUsage(routine.isInUsage());
            routineToBeReturned.setMeals(getMealsRoutineDTOList);
            routineToBeReturned.setWeekRepetitions(weekRepetitions);
            routineToBeReturned.setTotalCaloriesInTheDay(totalCalories);
            routineToBeReturned.setCreatedAt(routine.getCreatedAt());
            routineToBeReturned.setUpdatedAt(routine.getUpdatedAt());

            responseToReturn.add(routineToBeReturned);
        }

        return responseToReturn;
    }

    @Override
    @Transactional
    public GetPatientRoutineDTO createRoutines(CreatePatientRoutineDTO createRoutineDTO) throws ResourceNotFoundException {

        PatientRoutineModel routine = new PatientRoutineModel();

        routine.setName(createRoutineDTO.getName());
        routine.setInUsage(createRoutineDTO.getInUsage());
        routine.setInUsage(false);
        routine.setMonday(createRoutineDTO.getWeekRepetitions().getMonday());
        routine.setTuesday(createRoutineDTO.getWeekRepetitions().getTuesday());
        routine.setWednesday(createRoutineDTO.getWeekRepetitions().getWednesday());
        routine.setThursday(createRoutineDTO.getWeekRepetitions().getThursday());
        routine.setFriday(createRoutineDTO.getWeekRepetitions().getFriday());
        routine.setSaturday(createRoutineDTO.getWeekRepetitions().getSaturday());
        routine.setSunday(createRoutineDTO.getWeekRepetitions().getSunday());
        routine.setCreatedAt(LocalDateTime.now());
        routine.setUpdatedAt(LocalDateTime.now());

        List<PatientRoutineMealModel> patientRoutineMealModelList = new ArrayList<>();

        PatientModel patientModel = new PatientModel();

        for (CreatePatientMealsRoutineDTO item : createRoutineDTO.getMeals()) {

            PatientRoutineMealModel routineMeal = new PatientRoutineMealModel();

            Optional<PatientMealModel> mealFound = mealRepository.findById(item.getIdMeal());

            if (mealFound.isEmpty()) {
                throw new ResourceNotFoundException("Refeição não encontrada");
            }else{
                var meal = mealFound.get();

                routineMeal.setRoutine(routine);
                routineMeal.setNotifyAt(item.getNotifyAt());
                routineMeal.setMeal(meal);

                if (patientModel.getId() == null) patientModel = meal.getPatientModel();

                patientRoutineMealModelList.add(routineMeal);
            }

        }

        routine.setPatientModel(patientModel);

        PatientRoutineModel createdRoutine = repository.save(routine);

        List<PatientRoutineMealModel> createdRoutineMeals = patientRoutineMealRepository.saveAll(patientRoutineMealModelList);

        ConditionallyInsertRoutineMealsIntoNotificationsList(createdRoutine, createdRoutineMeals);

        return GetPatientRoutineDTO.builder()
                .id(createdRoutine.getId())
                .name(createRoutineDTO.getName())
                .inUsage( createRoutineDTO.getInUsage())
                .meals(getGetMealsRoutineDTO(createdRoutineMeals))
                .weekRepetitions(createRoutineDTO.getWeekRepetitions())
                .totalCaloriesInTheDay(getGetMealsRoutineDTO(createdRoutineMeals).stream().map(GetPatientMealsRoutineDTO::getTotalCalories).reduce(0.0, Double::sum))
                .inUsage(false)
                .createdAt(createdRoutine.getCreatedAt())
                .updatedAt(createdRoutine.getUpdatedAt())
                .build();

    }

    public void routineInUsage(UUID patientId, UUID id) throws ResourceNotFoundException {
        List<PatientRoutineModel> routines = repository.findAllByPatientId(patientId);
        Optional<PatientRoutineModel> routineToBeUpdated = repository.findById(id);

        if (routineToBeUpdated.isEmpty()) {
            throw new ResourceNotFoundException("Rotina não encontrada");
        }

        for (PatientRoutineModel routine : routines) {
            if (!routineToBeUpdated.get().getId().equals(routine.getId())) {
                if (areDaysMatching(routineToBeUpdated.get(), routine)) {
                    routine.setInUsage(false);
                    repository.save(routine);
                }
            }
        }

        routineToBeUpdated.get().setInUsage(!routineToBeUpdated.get().isInUsage());
        var routine = repository.save(routineToBeUpdated.get());

        for(var meal : routine.getRoutineMeal()) {
            mealsRoutineOfTheDayService.removeMeal(meal.getId());
        }

        ConditionallyInsertRoutineMealsIntoNotificationsList(routine, routine.getRoutineMeal());
    }

    private boolean areDaysMatching(PatientRoutineModel updatedRoutine, PatientRoutineModel existingRoutine) {
        return (updatedRoutine.isSunday() && existingRoutine.isSunday()) ||
                (updatedRoutine.isMonday() && existingRoutine.isMonday()) ||
                (updatedRoutine.isTuesday() && existingRoutine.isTuesday()) ||
                (updatedRoutine.isWednesday() && existingRoutine.isWednesday()) ||
                (updatedRoutine.isThursday() && existingRoutine.isThursday()) ||
                (updatedRoutine.isFriday() && existingRoutine.isFriday()) ||
                (updatedRoutine.isSaturday() && existingRoutine.isSaturday());
    }

    @Override
    public GetPatientRoutineDTO getRoutineInUsageBypatientId(UUID patientId) throws  ResourceNotFoundException{
        List<PatientRoutineModel> routines = repository.findAllByPatientId(patientId);

        var getRoutinesDTO = generateRoutineResponse(routines);

        // Get current time in UTC
        Instant utcNow = Instant.now();

        // Convert to Brazil time zone
        ZoneId brazilTimeZone = ZoneId.of("America/Sao_Paulo");
        LocalDateTime brazilTime = LocalDateTime.ofInstant(utcNow, brazilTimeZone);

        var today = brazilTime.getDayOfWeek().name();

        var getRoutinesDTOFiltered = getRoutinesDTO.stream().filter(GetPatientRoutineDTO::isInUsage).filter((routine)-> {
            return switch (today) {
                case "MONDAY" -> routine.getWeekRepetitions().getMonday();
                case "TUESDAY" -> routine.getWeekRepetitions().getTuesday();
                case "WEDNESDAY" -> routine.getWeekRepetitions().getWednesday();
                case "THURSDAY" -> routine.getWeekRepetitions().getThursday();
                case "FRIDAY" -> routine.getWeekRepetitions().getFriday();
                case "SATURDAY" -> routine.getWeekRepetitions().getSaturday();
                default -> routine.getWeekRepetitions().getSunday();
            };
        }).toList();

        if(getRoutinesDTOFiltered.size() != 1) {
            throw new ResourceNotFoundException("Não há rotinas em uso");
        }

        return getRoutinesDTOFiltered.get(0);
    }

    private static List<GetPatientMealsRoutineDTO> getGetMealsRoutineDTO(List<PatientRoutineMealModel> createdRoutineMeals) {

        List<GetPatientMealsRoutineDTO> getMealsRoutineDTOS = new ArrayList<>();

        for(var routineMeal  : createdRoutineMeals) {
            GetPatientMealsRoutineDTO mealRoutine = new GetPatientMealsRoutineDTO();

            mealRoutine.setId(routineMeal.getId());
            mealRoutine.setMealId(routineMeal.getMeal().getId());
            mealRoutine.setName(routineMeal.getMeal().getName());
            mealRoutine.setDescription(routineMeal.getMeal().getDescription());
            mealRoutine.setCategory(routineMeal.getMeal().getCategory());
            mealRoutine.setTotalCalories(routineMeal.getMeal().getTotalCalories());
            mealRoutine.setNotifyAt(routineMeal.getNotifyAt());
            mealRoutine.setPhoto(routineMeal.getMeal().getPhoto());

            getMealsRoutineDTOS.add(mealRoutine);
        }

        return getMealsRoutineDTOS;
    }

    @Override
    @Transactional
    public void updateRoutine(UpdatePatientRoutineDTO updateRoutineDTO) throws ResourceNotFoundException {
        Optional<PatientRoutineModel> routineToBeUpdated = repository.findById(updateRoutineDTO.getId());

        if (routineToBeUpdated.isEmpty())
            throw new ResourceNotFoundException("Rotina com id: " + updateRoutineDTO.getId() + " não encontrada");

        PatientRoutineModel routine = routineToBeUpdated.get();

        routine.setName(updateRoutineDTO.getName());
        routine.setInUsage(updateRoutineDTO.getInUsage());
        routine.setMonday(updateRoutineDTO.getWeekRepetitions().getMonday());
        routine.setTuesday(updateRoutineDTO.getWeekRepetitions().getTuesday());
        routine.setWednesday(updateRoutineDTO.getWeekRepetitions().getWednesday());
        routine.setThursday(updateRoutineDTO.getWeekRepetitions().getThursday());
        routine.setFriday(updateRoutineDTO.getWeekRepetitions().getFriday());
        routine.setSaturday(updateRoutineDTO.getWeekRepetitions().getSaturday());
        routine.setSunday(updateRoutineDTO.getWeekRepetitions().getSunday());
        routine.setUpdatedAt(LocalDateTime.now());

        List<PatientRoutineMealModel> patientRoutineMealModelList = new ArrayList<>();

        for(var meal : routine.getRoutineMeal()) {
            mealsRoutineOfTheDayService.removeMeal(meal.getId());
        }

        repository.deleteAllRoutineMealsByRoutineId(routine.getId());

        for (CreatePatientMealsRoutineDTO mealRoutine : updateRoutineDTO.getMeals()) {

            PatientRoutineMealModel routineMeal = new PatientRoutineMealModel();
            Optional<PatientMealModel> meal = mealRepository.findById(mealRoutine.getIdMeal());

            if (meal.isEmpty())
                throw new ResourceNotFoundException("Refeição com id: " + mealRoutine.getIdMeal() + " não encontrada");

            routineMeal.setRoutine(routine);
            routineMeal.setMeal(meal.get());
            routineMeal.setNotifyAt(mealRoutine.getNotifyAt());
        
            patientRoutineMealModelList.add(routineMeal);
        }

        PatientRoutineModel savedRoutine = repository.save(routine);

        List<PatientRoutineMealModel> createdRoutineMeals = patientRoutineMealRepository.saveAll(patientRoutineMealModelList);

        ConditionallyInsertRoutineMealsIntoNotificationsList(savedRoutine, createdRoutineMeals);
    }

    private void ConditionallyInsertRoutineMealsIntoNotificationsList(PatientRoutineModel routine, List<PatientRoutineMealModel> createdRoutineMeals) {
        boolean routineOccursToday = false;

        // Get current time in UTC
        Instant utcNow = Instant.now();

        // Convert to Brazil time zone
        ZoneId brazilTimeZone = ZoneId.of("America/Sao_Paulo");

        var today = LocalDate.now(brazilTimeZone).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        if(today.equals("Monday") && routine.isMonday()) routineOccursToday = true;
        else if(today.equals("Tuesday") && routine.isTuesday()) routineOccursToday = true;
        else if(today.equals("Wednesday") && routine.isWednesday()) routineOccursToday = true;
        else if(today.equals("Thursday") && routine.isThursday()) routineOccursToday = true;
        else if(today.equals("Friday") && routine.isFriday()) routineOccursToday = true;
        else if(today.equals("Saturday") && routine.isSaturday()) routineOccursToday = true;
        else if(today.equals("Sunday") && routine.isSunday()) routineOccursToday = true;

        if(routineOccursToday) {
            for(var routineMeal : createdRoutineMeals) {
                // Get the current time
                LocalTime currentBrazilTime = LocalTime.ofInstant(utcNow, brazilTimeZone);

                if(Duration.between(currentBrazilTime, routineMeal.getNotifyAt()).toMinutes() > 0) {
                    try {
                        mealsRoutineOfTheDayService.addMeal(routineMeal);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteRoutine(UUID id) throws ResourceNotFoundException {
        var routine = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Essa rotina não existe e portanto não pode ser deletada"));

        for(var meal : routine.getRoutineMeal()) {
            mealsRoutineOfTheDayService.removeMeal(meal.getId());
        }

        repository.deleteAllRoutineMealsByRoutineId(id);
        repository.deleteById(id);

    }
}
