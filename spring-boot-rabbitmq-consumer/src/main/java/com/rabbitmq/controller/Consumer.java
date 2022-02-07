package com.rabbitmq.controller;

import com.rabbitmq.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    @RabbitListener(queues = "queue")
    private void receive(Message msg){
        System.out.println(msg.getMessage());
    }
}

