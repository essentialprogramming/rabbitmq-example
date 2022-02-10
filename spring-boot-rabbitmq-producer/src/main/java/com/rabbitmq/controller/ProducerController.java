package com.rabbitmq.controller;

import com.rabbitmq.model.Message;
import com.rabbitmq.model.RoutingEnum;
import com.rabbitmq.service.ProducerService;
import com.rabbitmq.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping("/post")
    public JsonResponse send(@RequestBody final Message message, @NotNull @RequestParam final RoutingEnum routingEnum) {
        return producerService.sendMessage(message, routingEnum);
    }

    @PostMapping("/broadcast")
    public JsonResponse sendBroadcastMessage(@RequestBody final Message message) {
        return producerService.sendBroadcastMessage(message);
    }
}
