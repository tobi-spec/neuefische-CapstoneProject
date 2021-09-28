package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public AppUserEntity updatePassword(String userName, String setNewPassword) {
        String encodedPassword = passwordEncoder.encode(setNewPassword);
        appUserRepository.updatePasswordOfUser(userName, encodedPassword);
        Optional<AppUserEntity> changedUser = appUserRepository.findByUserName(userName);
        if (changedUser.isPresent()){
            return changedUser.get();
        }else {
            throw new EntityNotFoundException("Not such entry found");
        }
    }
}
