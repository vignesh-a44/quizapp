package com.vignesh.a44.quizapp.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Route {
    @GetMapping("/hello")
    public ResponseEntity<?> entry() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}