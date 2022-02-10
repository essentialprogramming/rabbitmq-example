package com.api.service;

import com.api.model.Message;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ConsumerService {

    private static final Logger LOG = getLogger(ConsumerService.class);

    @RabbitListener(queues = {"#{queueDirect.name}", "#{queueFanout.name}"})
    private void receiveMessage(final Message message) {
        LOG.info("Received message={}", message);
    }

    @RabbitListener(queues = "#{fanoutDlq.name}")
    private void processFailedMessages(final Message message) {
        LOG.info("Received failed message={}", message);
    }
}
