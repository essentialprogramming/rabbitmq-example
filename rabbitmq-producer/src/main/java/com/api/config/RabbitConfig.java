package com.api.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.api.env.resources.AppResources.*;
import static java.util.Arrays.asList;

@EnableRabbit
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitAdmin createAdmin() {
        final RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        asList(directExchange(), fanoutExchange(), deadLetterFanoutExchange())
                .forEach(admin::declareExchange);
        return admin;

    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RABBITMQ_EXCHANGE_DIRECT.value());
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RABBITMQ_EXCHANGE_FANOUT.value());
    }

    @Bean
    FanoutExchange deadLetterFanoutExchange() {
        return new FanoutExchange(RABBITMQ_EXCHANGE_FANOUT_DL.value());
    }

    @Bean
    public MessageConverter converter() {
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
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
