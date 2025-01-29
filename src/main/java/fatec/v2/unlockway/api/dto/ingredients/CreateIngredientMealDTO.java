package fatec.v2.unlockway.api.dto.ingredients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateIngredientMealDTO {
    private UUID id;
    private double amount;
}
