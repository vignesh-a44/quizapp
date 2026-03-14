package com.quiz.cloudservice.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiveService {
    @RabbitListener(queues = "${custom.rabbitmq.mail_queue}")
    public void receiveMessage(String message) {
        // Handle the received message here
        System.out.println("Received message: " + message);
    }
}
