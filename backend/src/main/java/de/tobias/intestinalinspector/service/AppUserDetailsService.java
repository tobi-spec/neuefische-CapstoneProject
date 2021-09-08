package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    AppUserRepository appUserRepository;

    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity appUser = appUserRepository
                .findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found " + username));

        return User.builder()
                .username(appUser.getUserName())
                .password(appUser.getUserPassword())
                .authorities("user")
                .build();
    }

    public Optional<AppUserEntity> findUser(String username){
        return appUserRepository.findByUserName(username);
    }
}
