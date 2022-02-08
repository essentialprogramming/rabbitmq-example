package com.api.service;

import com.api.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.api.config.RabbitConfig.*;

@Component
public class ConsumerService {

    @RabbitListener(queues = {QUEUE, FANOUT_QUEUE})
    private void receiveMessage(final Message message) {
        System.out.println(message.getMessage());
    }

    @RabbitListener(queues = FANOUT_DLQ)
    private void processFailedMessages(final Message message) {
        System.out.println(message.getMessage());
    }
}
