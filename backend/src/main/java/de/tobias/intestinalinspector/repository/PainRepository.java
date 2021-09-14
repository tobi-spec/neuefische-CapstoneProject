package de.tobias.intestinalinspector.repository;


import de.tobias.intestinalinspector.model.PainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PainRepository extends JpaRepository<PainEntity, Long> {
}
