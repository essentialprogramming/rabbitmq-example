package com.api.service;

import com.api.model.Message;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ConsumerService {

    private static final Logger LOG = getLogger(ConsumerService.class);

    @RabbitListener(queues = {"${app.rabbitmq.direct.queue}", "${app.rabbitmq.fanout.queue}"})
    private void receiveMessage(final Message message) {
        LOG.info("Received message={}", message);
        throw new RuntimeException("Failed");
    }

    @RabbitListener(queues = "${app.rabbitmq.fanout.queue.dl}")
    private void processFailedMessages(final Message message) {
        LOG.info("Received failed message={}", message);
    }
}
