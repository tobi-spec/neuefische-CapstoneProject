package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    List<FoodEntity> findAllByUserName(String userName);

}

