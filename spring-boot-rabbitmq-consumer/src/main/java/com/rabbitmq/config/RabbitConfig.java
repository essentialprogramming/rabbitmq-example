package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class RabbitConfig {

    public final String QUEUE = "queue";
    public final String FANOUT_QUEUE = "fanoutQueue";
    public final String EXCHANGE_DIRECT = "exchangeDirect";
    public final String EXCHANGE_FANOUT = "exchangeFanout";
    public final String ROUTING_A = "routingA";

    @Bean
    Queue queueA() {
        return new Queue(QUEUE, false);
    }

    @Bean
    Queue queueFanout() {
        return new Queue(FANOUT_QUEUE, false);
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
    Binding binding(DirectExchange exchange) {
        return bind(queueA()).to(exchange).with(ROUTING_A);
    }

    @Bean
    Binding bindingFanout(FanoutExchange exchange) {
        return bind(queueFanout()).to(exchange);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
