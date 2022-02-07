package com.api.controller;

import com.api.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Tag(description = "Queue", name = "")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Path("/producer")
public class ProducerController {

    private final RabbitTemplate rabbitTemplate;

    private final DirectExchange directExchange;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Operation(summary = "Send a message to queue.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Send a message to queue.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            })
    public String send(Message msg) {

        rabbitTemplate.convertAndSend(directExchange.getName(),"routingA",msg);
        return msg.toString();
    }

}
