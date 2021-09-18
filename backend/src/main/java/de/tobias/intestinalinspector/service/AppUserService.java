package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUserEntity create(AppUserEntity newUser) {
        AppUserEntity createdEntity = appUserRepository.save(newUser);
        return createdEntity;
    }
}
