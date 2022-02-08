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

    @RabbitListener(queues = "queue")
    private void receive(final Message msg) throws MessageException {
        LOG.info("Received message={}", msg);
        if( msg.getMessage().equals("string")) //for dead letter queue test
            throw new MessageException();
    }

    @RabbitListener(queues = "deadLetter.queue")
    public void processFailedMessages(Message message) {
        LOG.error("Received failed message: {}", message.toString());
    }
}
