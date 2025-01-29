package fatec.v2.unlockway.domain.repositories.nutritionist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistMealModel;

public interface NutritionistMealRepository extends JpaRepository<NutritionistMealModel, UUID> {
    List<NutritionistMealModel> findByPatientModelId(UUID idPatient);
    List<NutritionistMealModel> findByNutritionistModelId(UUID idNutritionist);
    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_meals WHERE recommendation_id = :idRecommendation")
    List<NutritionistMealModel> findByRecommendationId(@Param("idRecommendation") UUID idRecommendation);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionist_meals WHERE name = :name AND description = :description AND preparation_method = :preparationMethod AND original_meal_id = :originalMealId AND photo = :photo AND total_calories = :totalCalories")
    Optional<NutritionistMealModel> findBySeveralProperties(
        @Param("name") String name,
        @Param("description") String description,
        @Param("preparationMethod") String preparationMethod,
        @Param("originalMealId") UUID originalMealId,
        @Param("photo") String photo,
        @Param("totalCalories") Double totalCalories
    );

}
