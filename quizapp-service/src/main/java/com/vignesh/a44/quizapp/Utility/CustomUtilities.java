package com.vignesh.a44.quizapp.Utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class CustomUtilities {
    // Properties from application.properties
    @Value("${custom.security.unrestricted}")
    private List<String> nonRestrictedEndPoints;

    @Value("${custom.rabbitmq.mail_queue}")
    private String MAIL_QUEUE;

    @Value("${custom.rabbitmq.exchange}")
    private String QUEUE_EXCHANGE;

    @Value("${custom.rabbitmq.routing_key}")
    private String QUEUE_ROUTING_KEY;

    public List<String> getNonRestrictedRoutes() {
        return nonRestrictedEndPoints;
    }

    public String getMailQueue() {
        return MAIL_QUEUE;
    }

    public String getQueueExchange() {
        return QUEUE_EXCHANGE;
    }

    public String getQueueRoutingKey() {
        return QUEUE_ROUTING_KEY;
    }

    public static String generate6DigitCode() {
        final SecureRandom random = new SecureRandom();
        int code = random.nextInt(1_000_000); // 0 to 999999
        return String.format("%06d", code);
    }

}
