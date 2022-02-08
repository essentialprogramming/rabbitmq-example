package com.rabbitmq.controller;

import com.rabbitmq.model.Msg;
import org.slf4j.Logger;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class Producer {

    private static final Logger LOG = getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @PostMapping("/post")
    public String send(@RequestBody Msg msg) {
        LOG.info("Sending message={} to exchange={}", msg, directExchange.getName());
        rabbitTemplate.convertAndSend(directExchange.getName(), "routingA", msg);
        return "message: " + msg.getMessage()+ '\n' + "date: " + msg.getTimestamp().toString();
    }
}
