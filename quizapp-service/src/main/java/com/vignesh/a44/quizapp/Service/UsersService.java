package com.vignesh.a44.quizapp.Service;

import com.vignesh.a44.quizapp.Repository.UserRepo;
import com.vignesh.a44.quizapp.Schema.UsersSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UsersService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepo usersCollection;

    public ResponseEntity<?> verifyUser (String userEmail, String userPassword) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            UsersSchema user = usersCollection.findByEmail(userEmail);
            if (user != null) {
                System.out.println("::[UsersController]>> Inside for custom /login: "+user.toString());
                System.out.println("::[UsersController]>> Pre authenticationManager.authenticate ");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), userPassword));
                System.out.println("::[UsersController]>> Post authenticationManager.authenticate "+authentication.isAuthenticated());
                if(authentication.isAuthenticated()) {
                    String userToken = jwtService.generateToken(user.getUsername());
                    result.put("STATUS", "SUCCESS");
                    result.put("USER", user.getUsername());
                    result.put("USER_TOKEN", userToken);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            } else {
                result.put("STATUS", "FAILED");
                result.put("MESSAGE", "User not found!");
                return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Exception occurred while verifying user credentials: {}", e.getMessage());
            result.put("STATUS","FAILED");
            if (e.getMessage().equalsIgnoreCase("Bad credentials")) {
                result.put("MESSAGE", "Invalid email or password");
            } else {
                result.put("MESSAGE", "Error processing request");
            }
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

}
