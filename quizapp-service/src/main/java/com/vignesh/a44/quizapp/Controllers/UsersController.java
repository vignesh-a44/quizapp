package com.vignesh.a44.quizapp.Controllers;

import com.vignesh.a44.quizapp.Repository.UserRepo;
import com.vignesh.a44.quizapp.Schema.LoginRequestSchema;
import com.vignesh.a44.quizapp.Schema.UsersSchema;
import com.vignesh.a44.quizapp.Service.JWTService;
import com.vignesh.a44.quizapp.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepo usersCollection;

    @Autowired
    UsersService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUserRequest (@RequestBody UsersSchema user) {
        try {
            UsersSchema newUser = usersCollection.save(new UsersSchema(
                        user.getUsername(),
                        user.getEmail(),
                        encoder.encode(user.getPassword()),
                        user.isAuthor(),
                        user.isAdmin()
                    )
            );
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error while creating new user! - {}",e.getMessage());
            return new ResponseEntity<>("Could not create user: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestSchema loginRequest) {
        try {
            return userService.verifyUser(loginRequest.getEmail(), loginRequest.getPassword());
        } catch (Exception e) {
            log.error("Error while signing user in: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
