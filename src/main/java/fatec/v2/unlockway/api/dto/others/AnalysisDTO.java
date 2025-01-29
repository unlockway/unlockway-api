package fatec.v2.unlockway.api.dto.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalysisDTO {
    private int meals;
    private int routines;
    private int notifications;
    private int recommendations;
    private List<Double> weekCalories;
}
