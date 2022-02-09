package com.api.model;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum RoutingEnum {

   SPRINGBOOT_RABBITMQ_CONSUMER(1,"SPRINGBOOT_RABBITMQ_CONSUMER"),
   RABBITMQ_CONSUMER(2,"RABBITMQ_CONSUMER"),
   DEAD_LETTER_CONSUMER(3,"DEAD_LETTER_CONSUMER");

    private final Integer id;
    private final String routing;

    RoutingEnum(Integer id, String routing) {
        this.id = id;
        this.routing = routing;
    }

    public static RoutingEnum fromId(int id) {
        return Stream.of(RoutingEnum.values())
                .filter(routing -> routing.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No RoutingEnum with id : " + id + " found."));
    }

}
