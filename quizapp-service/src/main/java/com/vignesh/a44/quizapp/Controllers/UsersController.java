package com.vignesh.a44.quizapp.Controllers;

import com.vignesh.a44.quizapp.Schema.LoginRequestSchema;
import com.vignesh.a44.quizapp.Schema.UsersSchema;
import com.vignesh.a44.quizapp.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUserRequest (@RequestBody UsersSchema user) {
        try {
            UsersSchema newUser = userService.createUser(user);
            newUser.setVerificationProcess(userService.startVerificationProcess(newUser.getEmail()));
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Duplicate Request")) {
                return new ResponseEntity<>("An account with given email address already exists!", HttpStatus.CONFLICT);
            }
            log.error("Error while creating new user! - {}",e.getMessage());
            return new ResponseEntity<>("Could not create user: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestSchema loginRequest) {
        try {
            return userService.authorizeUser(loginRequest.getEmail(), loginRequest.getPassword());
        } catch (Exception e) {
            log.error("Error while signing user in: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUserStatus(@RequestParam String userEmail) {
        try {
            return userService.verifyUser(userEmail);
        } catch (Exception e) {
            log.error("Exception while verifying user status for user - {} {}", userEmail, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
