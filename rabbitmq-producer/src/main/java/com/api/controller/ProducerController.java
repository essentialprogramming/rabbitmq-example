package com.api.controller;

import com.api.model.Message;
import com.api.service.ProducerService;
import com.util.web.JsonResponse;
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
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

import static com.util.async.ExecutorsProvider.getExecutorService;
import static com.util.async.Computation.computeAsync;
import static javax.ws.rs.core.Response.ok;
import static com.exception.ExceptionHandler.handleException;

@Tag(description = "Queue", name = "")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Path("/producer")
public class ProducerController {

    private final ProducerService producerService;

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
    public void send(Message msg, @Suspended AsyncResponse asyncResponse) {

        final ExecutorService executorService = getExecutorService();
        computeAsync(() -> producerService.getMessage(msg), executorService)
                .thenApplyAsync(json -> asyncResponse.resume(ok(json).build()), executorService)
                .exceptionally(error -> asyncResponse.resume(handleException((CompletionException) error)));
    }

}
