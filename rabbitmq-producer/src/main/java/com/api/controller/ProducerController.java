package com.api.controller;

import com.api.model.Message;
import com.api.model.RoutingEnum;
import com.api.service.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

import static com.exception.ExceptionHandler.handleException;
import static com.util.async.Computation.computeAsync;
import static com.util.async.ExecutorsProvider.getExecutorService;
import static javax.ws.rs.core.Response.ok;

@Tag(description = "Queue", name = "")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Path("/publish")
public class ProducerController {

    private final ProducerService producerService;

    @POST
    @Path("/direct")
    @Consumes("application/json")
    @Produces("application/json")
    @Operation(summary = "Send a message to queue.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Send a message to queue.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            })
    public void sendMessage(@NotNull @QueryParam("RoutingEnum") RoutingEnum routingEnum,
                            Message message,
                            @Suspended AsyncResponse asyncResponse) {

        final ExecutorService executorService = getExecutorService();
        computeAsync(() -> producerService.sendMessage(message, routingEnum.getRouting()), executorService)
                .thenApplyAsync(json -> asyncResponse.resume(ok(json).build()), executorService)
                .exceptionally(error -> asyncResponse.resume(handleException((CompletionException) error)));
    }

    @POST
    @Path("/broadcast")
    @Consumes("application/json")
    @Produces("application/json")
    @Operation(summary = "Send a message to fanout.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Send a message to fanout.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            })
    public void sendBroadcastMessage(Message message, @Suspended AsyncResponse asyncResponse) {

        final ExecutorService executorService = getExecutorService();
        computeAsync(() -> producerService.sendBroadcastMessage(message), executorService)
                .thenApplyAsync(json -> asyncResponse.resume(ok(json).build()), executorService)
                .exceptionally(error -> asyncResponse.resume(handleException((CompletionException) error)));
    }
}
