package fatec.v2.unlockway.domain.repositories.nutritionist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fatec.v2.unlockway.domain.entity.nutritionist.NutritionistModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NutritionistRepository extends JpaRepository<NutritionistModel, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionists WHERE email = :email")
    Optional<NutritionistModel> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_nutritionists WHERE cfn = :cfn")
    Optional<NutritionistModel> findByCfn(String cfn);
}
