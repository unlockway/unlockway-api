package fatec.v2.unlockway.api.dto.ingredients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import fatec.v2.unlockway.domain.enums.Measure;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetIngredientMealDTO {
    private UUID id;
    private String name;
    private Measure measure;
    private double amount;
}
