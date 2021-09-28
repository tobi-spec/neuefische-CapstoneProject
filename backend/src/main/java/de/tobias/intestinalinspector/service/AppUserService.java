package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUserEntity create(AppUserEntity newUser) {

        newUser.setUserRole("user");
        newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
        AppUserEntity createdEntity = appUserRepository.save(newUser);
        return createdEntity;
    }

    public Optional<AppUserEntity> findUser(String username){
        return appUserRepository.findByUserName(username);
    }

    @Transactional
    public AppUserEntity updatePassword(String userName, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        Optional<AppUserEntity> entityToChange = appUserRepository.findByUserName(userName);
        if (entityToChange.isPresent()){
            entityToChange.get().setUserPassword(encodedPassword);
            return appUserRepository.save(entityToChange.get());
        }else {
            throw new EntityNotFoundException("Not such entry found");
        }
    }
}
