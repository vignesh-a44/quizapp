package com.vignesh.a44.quizapp.Service;

import com.vignesh.a44.quizapp.Repository.UserRepo;
import com.vignesh.a44.quizapp.Schema.UserPrincipal;
import com.vignesh.a44.quizapp.Schema.UsersSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserRepo repo;

    /*The Actual parameter passed here is user email*/
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        try {
            log.info("Requesting user details for user : {}", userEmail);
            UsersSchema user = repo.findByEmail(userEmail);
            if (user == null) {
                log.debug("No user found for : {}", userEmail);
                throw new UsernameNotFoundException("No user found with provided userEmail");
            }
            return new UserPrincipal(user);
        } catch (Exception e) {
            log.error("Error while loading user details with userEmail: {}", e.getMessage());
            return null;
        }
    }
}
