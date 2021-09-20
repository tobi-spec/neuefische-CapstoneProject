package de.tobias.intestinalinspector.repository;


import de.tobias.intestinalinspector.model.PainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PainRepository extends JpaRepository<PainEntity, Long> {

    List<PainEntity> findAllByUserNameOrderByDate(String username);

    // .save() can not be used to overwrite, because date would be also overwritten with current date.
    @Modifying
    @Transactional
    @Query("UPDATE PainEntity f SET f.painLevel = :painLevel WHERE f.id = :id")
    void updateFoodFromUser(@Param("id") long id, @Param("painLevel") int painLevel);
}
