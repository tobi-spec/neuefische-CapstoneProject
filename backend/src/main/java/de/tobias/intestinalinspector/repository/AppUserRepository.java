package de.tobias.intestinalinspector.repository;

import de.tobias.intestinalinspector.model.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByUserName(String name);

    void deleteAppUserEntityByUserName( String name);

}
