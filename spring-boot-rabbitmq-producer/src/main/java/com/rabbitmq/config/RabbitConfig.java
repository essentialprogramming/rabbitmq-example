package com.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public final String EXCHANGE_DIRECT = "exchangeDirect";
    public final String EXCHANGE_FANOUT = "exchangeFanout";
    public final String EXCHANGE_FANOUT_DEAD_LETTER = "exchangeFanoutDeadLetter";
    public final String EXCHANGE_DIRECT_DEAD_LETTER = "exchangeDirectDeadLetter";

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    DirectExchange deadLetterDirectExchange() {
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
