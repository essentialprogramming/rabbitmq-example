package com.api.service;

import com.api.model.Message;
import com.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    private final DirectExchange directExchange;

    public JsonResponse getMessage(Message message) {

        rabbitTemplate.convertAndSend(directExchange.getName(),"routingA",message);
        return new JsonResponse().with("message", message.getMessage())
                                 .with("date", message.getTimestamp().toString());

    }
}
