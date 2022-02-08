package com.rabbitmq.service;

import com.rabbitmq.model.Message;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ConsumerService {

    private static final Logger LOG = getLogger(ConsumerService.class);

    @RabbitListener(queues = {"queue", "fanoutQueue"})
    private void receive(final Message message) {
        LOG.info("Received message={}", message);
    }
}
