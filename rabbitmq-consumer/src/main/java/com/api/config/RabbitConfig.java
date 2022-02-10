package com.api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

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
        return QueueBuilder.durable(RABBITMQ_DIRECT_QUEUE.value())
                .withArgument("x-dead-letter-exchange", RABBITMQ_EXCHANGE_DIRECT_DL.value())
                .withArgument("x-dead-letter-routing-key", RABBITMQ_DIRECT_ROUTING_KEY_DL.value())
                .build();
    }

    @Bean
    Queue directDlq() {
        return QueueBuilder.durable(RABBITMQ_DIRECT_QUEUE_DL.value()).build();
    }

    @Bean
    Queue queueFanout() {
        return QueueBuilder.durable(RABBITMQ_EXCHANGE_FANOUT.value())
                .withArgument("x-dead-letter-exchange", RABBITMQ_EXCHANGE_FANOUT_DL.value())
                .build();
    }

    @Bean
    Queue fanoutDlq() {
        return QueueBuilder.durable(RABBITMQ_FANOUT_QUEUE_DL.value()).build();
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RABBITMQ_EXCHANGE_DIRECT.value());
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(RABBITMQ_EXCHANGE_DIRECT_DL.value());
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
        return bind(queueDirect()).to(directExchange()).with(RABBITMQ_DIRECT_ROUTING_KEY.value().toString());
    }

    @Bean
    Binding bindingDirectDLQ() {
        return bind(directDlq()).to(deadLetterExchange()).with(RABBITMQ_DIRECT_ROUTING_KEY_DL.value().toString());
    }

    @Bean
    Binding bindingFanout() {
        return bind(queueFanout()).to(fanoutExchange());
    }

    @Bean
    Binding bindingFanoutDLQ() {
        return bind(fanoutDlq()).to(deadLetterFanoutExchange());
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
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(final ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setMaxConcurrentConsumers(RABBITMQ_MAX_CONSUMERS.value());
        factory.setAdviceChain(retriesSetting());
        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retriesSetting() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .backOffOptions(3000, 2, 10000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    private void declareAll(final RabbitAdmin admin) {
        asList(queueDirect(), queueFanout(), fanoutDlq(), directDlq())
                .forEach(admin::declareQueue);
        asList(directExchange(), fanoutExchange(), deadLetterFanoutExchange(), deadLetterExchange())
                .forEach(admin::declareExchange);
        asList(bindingDirect(), bindingFanout(), bindingFanoutDLQ(), bindingDirectDLQ())
                .forEach(admin::declareBinding);
    }
}
