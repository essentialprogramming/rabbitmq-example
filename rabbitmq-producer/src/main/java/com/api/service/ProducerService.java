package com.api.service;

import com.api.model.Message;
import com.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    private final FanoutExchange fanoutExchange;

    public JsonResponse sendMessage(final Message message, final String routeKey) {
        rabbitTemplate.convertAndSend(directExchange.getName(), routeKey, message);
        return new JsonResponse().with("message", message.getMessage());
    }

    public JsonResponse sendBroadcastMessage(final Message message) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), EMPTY, message);
        return new JsonResponse().with("message", message);
    }
}
