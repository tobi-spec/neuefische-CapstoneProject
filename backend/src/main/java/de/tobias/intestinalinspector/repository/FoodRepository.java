package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    FoodEntity findFirstByOrderByIdDesc();
}

