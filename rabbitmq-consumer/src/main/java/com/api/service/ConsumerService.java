package com.api.service;

import com.api.model.Message;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.api.config.RabbitConfig.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ConsumerService {

    private static final Logger LOG = getLogger(ConsumerService.class);

    @RabbitListener(queues = {DIRECT_QUEUE, FANOUT_QUEUE})
    private void receiveMessage(final Message message) {
        LOG.info("Received message={}", message);
        throw new RuntimeException("Failed");
    }

    @RabbitListener(queues = FANOUT_DLQ)
    private void processFailedMessages(final Message message) {
        LOG.info("Received failed message={}", message);
    }
}
