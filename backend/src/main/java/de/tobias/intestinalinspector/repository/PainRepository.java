package de.tobias.intestinalinspector.repository;


import de.tobias.intestinalinspector.model.PainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PainRepository extends JpaRepository<PainEntity, Long> {

    List<PainEntity> findAllByUserNameOrderByDate(String username);
}
