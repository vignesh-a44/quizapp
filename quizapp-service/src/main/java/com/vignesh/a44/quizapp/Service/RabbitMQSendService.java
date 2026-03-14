package com.vignesh.a44.quizapp.Service;

import com.vignesh.a44.quizapp.Utility.CustomUtilities;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

class MailQueueMessage {
    private String toAddress;
    private String topic;
    private String message;

    public MailQueueMessage(String toAddress, String topic, String message) {
        this.toAddress = toAddress;
        this.topic = topic;
        this.message = message;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "content: {" +
                "toAddress: '" + toAddress + '\'' +
                ", topic: '" + topic + '\'' +
                ", message: '" + message + '\'' +
                '}';
    }
}

@Service
public class RabbitMQSendService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    CustomUtilities utilities;

    public RabbitMQSendService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(
                utilities.getQueueExchange(),
                utilities.getQueueRoutingKey(),
                message
        );
    }
}
