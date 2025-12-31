package com.vignesh.a44.quizapp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Route {

    private static final Logger log = LoggerFactory.getLogger(Route.class);

    @GetMapping("/hello")
    public ResponseEntity<?> entry() {
        log.info("Quiz service ping received!!!");
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

}