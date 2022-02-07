package com.api.service;

import com.api.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    @RabbitListener(containerFactory="rabbitListenerContainerFactory", queues = "queue")
    public void receiveMessage(Message message) {
        System.out.println(message.getMessage());
    }
}
