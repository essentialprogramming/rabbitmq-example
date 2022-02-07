package com.avangarde.rabbitmq.springbootrabbitmqproducer.controller;

import com.avangarde.rabbitmq.springbootrabbitmqproducer.model.Msg;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @PostMapping("/post")
    public String send(@RequestBody Msg msg) {

        rabbitTemplate.convertAndSend(directExchange.getName(),"routingA",msg);
        return msg.toString();
    }
}
