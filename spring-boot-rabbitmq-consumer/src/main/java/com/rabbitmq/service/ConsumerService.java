package com.rabbitmq.service;

import com.rabbitmq.exceptions.MessageException;
import com.rabbitmq.model.Message;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ConsumerService {

    private static final Logger LOG = getLogger(ConsumerService.class);

    @RabbitListener(queues = {"${app.rabbitmq.direct.queue}", "${app.rabbitmq.fanout.queue}"})
    private void receive(final Message message) throws MessageException {
        LOG.info("Received message={}", message);
        if (message.getMessage().equals("string"))
            throw new MessageException();
    }

    @RabbitListener(queues = {"${app.rabbitmq.direct.queue.dl}", "${app.rabbitmq.fanout.queue.dl}"})
    private void processFailedMessages(final Message message) {
        LOG.info("Received failed message={}", message);
    }
}
