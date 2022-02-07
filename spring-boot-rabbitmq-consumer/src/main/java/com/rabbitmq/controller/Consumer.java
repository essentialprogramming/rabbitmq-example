package com.rabbitmq.controller;

import com.rabbitmq.model.Msg;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    @RabbitListener(queues = "queue")
    private void receive(Msg msg){
        System.out.println(msg.getId());
    }
}

