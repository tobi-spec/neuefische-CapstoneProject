package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    List<FoodEntity> findAllByUserNameOrderByDate(String userName);

    // .save() can not be used to overwrite, because date would be also overwritten with current date.
    @Modifying
    @Transactional
    @Query("UPDATE FoodEntity f SET f.foodName = :foodName WHERE f.id = :id")
    void updateFoodFromUser(@Param("id") long id, @Param("foodName") String foodName);



}

