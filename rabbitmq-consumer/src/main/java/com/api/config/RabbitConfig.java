package com.api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@EnableRabbit
@Configuration
public class RabbitConfig {

    public static final String QUEUE = "queue";
    public static final String FANOUT_QUEUE = "fanoutQueue";
    public static final String FANOUT_DLQ = "fanoutDeadLetterQueue";
    public static final String EXCHANGE_DIRECT = "exchangeDirect";
    public static final String EXCHANGE_FANOUT = "exchangeFanout";
    public static final String EXCHANGE_FANOUT_DEAD_LETTER = "exchangeFanoutDeadLetter";
    public static final String ROUTING_A = "routingA";

    @Bean
    Queue queueA() {
        return QueueBuilder.durable(QUEUE).build();
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
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    FanoutExchange deadLetterFanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT_DEAD_LETTER);
    }

    @Bean
    Binding bindingDirect() {
        return bind(queueA()).to(directExchange()).with(ROUTING_A);
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
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
       return connectionFactory;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
}
