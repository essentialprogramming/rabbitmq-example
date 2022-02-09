package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.exchange.direct}")
    private String exchangeDirect;
    @Value("${app.rabbitmq.exchange.fanout}")
    private String exchangeFanout;
    @Value("${app.rabbitmq.exchange.direct.dl}")
    private String exchangeDirectDL;
    @Value("${app.rabbitmq.exchange.fanout.dl}")
    private String exchangeFanoutDL;
    @Value("${app.rabbitmq.direct.queue}")
    private String directQueue;
    @Value("${app.rabbitmq.direct.queue.dl}")
    private String directDLQ;
    @Value("${app.rabbitmq.fanout.queue}")
    private String fanoutQueue;
    @Value("${app.rabbitmq.fanout.queue.dl}")
    private String fanoutDLQ;
    @Value("${app.rabbitmq.direct.routingKey}")
    private String routingKey;
    @Value("${app.rabbitmq.direct.routingKey.dl}")
    private String routingKeyDL;

    @Bean
    Queue queueDirect() {
        return QueueBuilder.durable(directQueue)
                .withArgument("x-dead-letter-exchange", exchangeDirectDL)
                .withArgument("x-dead-letter-routing-key", routingKeyDL)
                .build();
    }

    @Bean
    Queue directDLQ() {
        return QueueBuilder.durable(directDLQ).build();
    }

    @Bean
    Queue queueFanout() {
        return QueueBuilder.durable(fanoutQueue)
                .withArgument("x-dead-letter-exchange", exchangeFanoutDL)
                .build();
    }

    @Bean
    Queue fanoutDLQ() {
        return QueueBuilder.durable(fanoutDLQ).build();
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeDirect);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(exchangeDirectDL);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchangeFanout);
    }

    @Bean
    FanoutExchange deadLetterFanoutExchange() {
        return new FanoutExchange(exchangeFanoutDL);
    }

    @Bean
    Binding bindingDirect() {
        return bind(queueDirect()).to(directExchange()).with(routingKey);
    }

    @Bean
    Binding bindingDirectDLQ() {
        return bind(directDLQ()).to(deadLetterExchange()).with(routingKeyDL);
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
