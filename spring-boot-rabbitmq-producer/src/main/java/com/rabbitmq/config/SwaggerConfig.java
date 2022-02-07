package com.rabbitmq.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi rabbitProducerAPI() {
        final String[] packagesToScan = {"com.rabbitmq"};
        return GroupedOpenApi
                .builder()
                .group("RabbitMQ Producer API")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/post")
                .addOpenApiCustomiser(statusApiCustomizer())
                .build();
    }

    private OpenApiCustomiser statusApiCustomizer() {
        return openAPI -> openAPI
                .info(new Info()
                        .title("Springboot & OpenAPI")
                        .description("This is a sample Spring Boot RESTful service using OpenAPI")
                        .version("3.0.0")
                        .contact(new Contact()
                                .name("Avangarde Software")
                                .url("https://avangarde-software.com/")
                                .email("markos.kosa@avangarde-software.com")));
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Contact Application API").description(
                        "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
    }
}