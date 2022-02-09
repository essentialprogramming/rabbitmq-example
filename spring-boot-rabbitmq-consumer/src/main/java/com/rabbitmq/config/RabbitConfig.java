package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class RabbitConfig {

    public static final String DIRECT_QUEUE = "directQueue";
    public static final String DIRECT_DLQ = "directDeadLetterQueue";
    public static final String FANOUT_QUEUE = "fanoutQueue";
    public static final String FANOUT_DLQ = "fanoutDeadLetterQueue";
    public static final String EXCHANGE_DIRECT = "exchangeDirect";
    public static final String EXCHANGE_FANOUT = "exchangeFanout";
    public static final String EXCHANGE_FANOUT_DEAD_LETTER = "exchangeFanoutDeadLetter";
    public static final String EXCHANGE_DIRECT_DEAD_LETTER = "exchangeDirectDeadLetter";
    public static final String DLQ_ROUTING_KEY = "deadLetterKey";
    public static final String DIRECT_ROUTING = "SPRINGBOOT_RABBITMQ_CONSUMER";

    @Bean
    Queue queueA(){
        return QueueBuilder.durable(DIRECT_QUEUE)
                .withArgument("x-dead-letter-exchange", EXCHANGE_DIRECT_DEAD_LETTER)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable(DIRECT_DLQ).build();
    }

    @Bean
    Queue queueFanout() {
        return QueueBuilder.durable(FANOUT_QUEUE)
                .withArgument("x-dead-letter-exchange", EXCHANGE_FANOUT_DEAD_LETTER)
                .build();
    }

    @Bean
    Queue fanoutDLQ() {
        return QueueBuilder.durable(FANOUT_DLQ).build();
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(EXCHANGE_DIRECT_DEAD_LETTER);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    FanoutExchange deadLetterFanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT_DEAD_LETTER);
    }

    @Bean
    Binding bindingDirect() {
        return bind(queueA()).to(directExchange()).with(DIRECT_ROUTING);
    }

    @Bean
    Binding bindingDirectDLQ() {
        return bind(dlq()).to(deadLetterExchange()).with(DLQ_ROUTING_KEY);
    }

    @Bean
    Binding bindingFanout() {
        return bind(queueFanout()).to(fanoutExchange());
    }

    @Bean
    Binding bindingFanoutDLQ() {
        return bind(fanoutDLQ()).to(deadLetterFanoutExchange());
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
