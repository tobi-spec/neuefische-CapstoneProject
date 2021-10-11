package de.tobias.intestinalinspector.repository;


import de.tobias.intestinalinspector.model.PainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PainRepository extends GenericDBRepository<PainEntity> {
}
