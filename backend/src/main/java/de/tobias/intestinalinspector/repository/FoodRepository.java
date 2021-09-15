package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    List<FoodEntity> findAllByUserName(String userName);

    @Modifying
    @Transactional
    @Query("UPDATE FoodEntity f SET f.foodName = :foodName WHERE f.id = :id")
    int updateFood(@Param("id") long id, @Param("foodName") String foodName);

}

