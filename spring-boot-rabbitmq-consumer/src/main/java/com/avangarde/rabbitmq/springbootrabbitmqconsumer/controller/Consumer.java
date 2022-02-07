package com.avangarde.rabbitmq.springbootrabbitmqconsumer.controller;

import com.avangarde.rabbitmq.springbootrabbitmqconsumer.model.Msg;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    @RabbitListener(queues = "queue")
    private void receive(Msg msg){
        System.out.println(msg.getId());
    }
}

