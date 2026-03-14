package com.vignesh.a44.quizapp.Controllers;

import com.vignesh.a44.quizapp.Service.RabbitMQSendService;
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
    RabbitMQSendService rabbitMQSend;

    @GetMapping("/hello")
    public ResponseEntity<?> entry() {
        log.info("Quiz service ping received!!!");
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @PostMapping("/mail/send")
    public ResponseEntity<?> sendMail(@RequestBody String content) {
        try {
            rabbitMQSend.sendMessage(content);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while sending message to RabbitMq: {}",e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}