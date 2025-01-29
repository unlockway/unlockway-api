package fatec.v2.unlockway.api.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import fatec.v2.unlockway.api.dto.others.WeekRepetitionsDTO;
import fatec.v2.unlockway.api.dto.history.meals.GetHistoryMealDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHistoryDTO {
    private UUID idRoutine;
    private String name;
    private LocalDate date;
    private double totalCaloriesInTheDay;
    private List<GetHistoryMealDTO> meals;
    private WeekRepetitionsDTO weekRepetitions;
}
