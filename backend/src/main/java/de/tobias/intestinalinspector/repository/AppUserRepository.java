package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByUserName(String name);


    @Modifying
    @Transactional
    @Query("UPDATE AppUserEntity u SET u.userPassword = :encodedPassword WHERE u.userName = :userName")
    void updatePasswordOfUser(@Param("userName") String userName, @Param("encodedPassword") String encodedPassword);
}
