package com.rabbitmq.service;

import com.rabbitmq.model.Message;
import com.rabbitmq.model.RoutingEnum;
import com.rabbitmq.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProducerService {

    private static final Logger LOG = getLogger(ProducerService.class);

    private final RabbitTemplate rabbitTemplate;

    public JsonResponse sendMessage(final Message message, RoutingEnum routingEnum) {
        LOG.info("Sending message={} to exchange={}", message, "exchangeDirect");
        rabbitTemplate.convertAndSend("exchangeDirect", routingEnum.getRouting() , message);
        return new JsonResponse()
                .with("message", message.getMessage())
                .with("date", message.getTimestamp());
    }
}
