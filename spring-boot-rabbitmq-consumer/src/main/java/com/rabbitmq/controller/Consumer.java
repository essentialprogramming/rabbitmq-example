package com.rabbitmq.controller;

import com.rabbitmq.model.Msg;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class Consumer {

    private static final Logger LOG = getLogger(Consumer.class);

    @RabbitListener(queues = "queue")
    private void receive(final Msg msg) {
        LOG.info("Received message={}", msg);
    }
}

