package com.api.config;

import org.springframework.amqp.core.*;
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
import static org.springframework.amqp.core.BindingBuilder.bind;

@EnableRabbit
@Configuration
public class RabbitConfig {


    @Bean
    public RabbitAdmin createAdmin() {
        final RabbitAdmin admin = new RabbitAdmin(connectionFactory());
        declareAll(admin);
        return admin;
    }

    @Bean
    Queue queueDirect() {
        return QueueBuilder.durable(RABBITMQ_DIRECT_QUEUE.value()).build();
    }

    @Bean
    Queue queueFanout() {
        return QueueBuilder.durable(RABBITMQ_EXCHANGE_FANOUT.value())
                .withArgument("x-dead-letter-exchange", RABBITMQ_EXCHANGE_FANOUT_DL)
                .build();
    }

    @Bean
    Queue fanoutDLQ() {
        return QueueBuilder.durable(RABBITMQ_FANOUT_QUEUE_DL.value()).build();
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
    Binding bindingDirect() {
        return bind(queueDirect()).to(directExchange()).with(RABBITMQ_DIRECT_ROUTING_KEY);
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

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(RABBITMQ_HOST.value().toString());
        connectionFactory.setUsername(RABBITMQ_USERNAME.value());
        connectionFactory.setPassword(RABBITMQ_PASSWORD.value());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(final ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setMaxConcurrentConsumers(RABBITMQ_MAX_CONSUMERS.value());
        return factory;
    }

    private void declareAll(final RabbitAdmin admin) {
        asList(queueDirect(), queueFanout(), fanoutDLQ())
                .forEach(admin::declareQueue);
        asList(directExchange(), fanoutExchange(), deadLetterFanoutExchange())
                .forEach(admin::declareExchange);
        asList(bindingDirect(), bindingFanout(), bindingFanoutDLQ())
                .forEach(admin::declareBinding);
    }
}
