package com.vignesh.a44.quizapp.Controllers;

import com.vignesh.a44.quizapp.Schema.LoginRequestSchema;
import com.vignesh.a44.quizapp.Schema.UsersSchema;
import com.vignesh.a44.quizapp.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Route {

    private static final Logger log = LoggerFactory.getLogger(Route.class);

    @Autowired
    UsersService userService;

    @GetMapping("/hello")
    public ResponseEntity<?> entry() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequestSchema request) {
        try {
            return userService.verifyUser(request.getEmail(), request.getPassword());
        } catch (Exception e) {
            log.error("Error while logging in user: {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}