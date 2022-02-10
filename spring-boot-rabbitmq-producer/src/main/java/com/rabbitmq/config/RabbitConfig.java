package com.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.exchange.direct}")
    private String exchangeDirect;
    @Value("${app.rabbitmq.exchange.fanout}")
    private String exchangeFanout;
    @Value("${app.rabbitmq.exchange.direct.deadLetter}")
    private String exchangeDirectDL;
    @Value("${app.rabbitmq.exchange.fanout.deadLetter}")
    private String exchangeFanoutDL;

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeDirect);
    }

    @Bean
    DirectExchange deadLetterDirectExchange() {
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
