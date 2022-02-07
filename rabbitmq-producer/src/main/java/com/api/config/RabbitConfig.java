package com.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    public final String QUEUE = "queue";
    public final String EXCHANGEDIRECT = "exchangeDirect";
    public final String ROUTINGA = "routingA";

    @Bean
    Queue queueA(){
        return new Queue(QUEUE,false);
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(EXCHANGEDIRECT);
    }

    @Bean
    Binding binding(Queue queueA, DirectExchange exchange){
        return  BindingBuilder.bind(queueA).to(exchange).with(ROUTINGA);
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
}
