package com.api.controller;

import com.api.model.UserInput;
import com.api.output.UserJSON;
import com.api.service.UserService;
import com.exception.ExceptionHandler;
import com.util.async.Computation;
import com.util.async.ExecutorsProvider;
import com.util.enums.HTTPCustomStatus;
import com.util.enums.Language;
import com.util.exceptions.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

@Tag(description = "User API", name = "User Services")
@Path("/v1/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;


    @POST
    @Path("user/create")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Return user if successfully added",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserJSON.class)))
            })
    public void createUser(@Valid UserInput userInput, @Suspended AsyncResponse asyncResponse) {

        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        Computation.computeAsync(() -> createUser(userInput), executorService)
                .thenApplyAsync(json -> asyncResponse.resume(Response.status(201).entity(json).build()), executorService)
                .exceptionally(error -> asyncResponse.resume(ExceptionHandler.handleException((CompletionException) error)));

    }

    private Serializable createUser(UserInput userInput) throws GeneralSecurityException, ApiException {
        boolean isValid = userService.checkAvailabilityByEmail(userInput.getEmail());

        if (!isValid) {
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Email is already used!");
        }
        return userService.save(userInput);

    }

}