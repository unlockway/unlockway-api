package fatec.v2.unlockway.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fatec.v2.unlockway.domain.entity.MealsOfTheDay;

import java.util.UUID;

@Repository
public interface MealsOfTHeDayRepository extends JpaRepository<MealsOfTheDay, UUID> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_meals_of_the_day tmofd WHERE tmofd.routine_meal_id = :routinemealid")
    void deleteByRoutineMealId(@Param("routinemealid") UUID routineMealId);
}
