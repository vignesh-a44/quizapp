package com.vignesh.a44.quizapp.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RabbitMQConfiguration {

    @Value("${custom.rabbitmq.mail_queue}")
    public String MAIL_QUEUE;

    @Value("${custom.rabbitmq.exchange}")
    public String EXCHANGE;

    @Value("${custom.rabbitmq.routing_key}")
    public String ROUTING_KEY;

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE, true);
    }

    @Bean
    public DirectExchange quizAppExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding quizAppBinder(Queue quizQueue, DirectExchange quizExchange) {
        return BindingBuilder
                .bind(quizQueue)
                .to(quizExchange)
                .with(ROUTING_KEY);
    }

}
