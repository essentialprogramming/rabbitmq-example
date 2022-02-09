package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public final String QUEUE = "queue";
    public final String EXCHANGEDIRECT = "exchangeDirect";

    @Bean
    Queue queueA(){
        return QueueBuilder.durable(QUEUE).withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable("deadLetter.queue").build();
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange("deadLetterExchange");
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(EXCHANGEDIRECT);
    }

    @Bean
    Binding binding(Queue queueA, DirectExchange exchange){
        return  BindingBuilder.bind(queueA).to(exchange).with("SPRINGBOOT_RABBITMQ_CONSUMER");
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
    }


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
