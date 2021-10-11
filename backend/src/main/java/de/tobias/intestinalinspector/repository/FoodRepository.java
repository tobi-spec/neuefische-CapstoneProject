package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends GenericDBRepository<FoodEntity> {
}

