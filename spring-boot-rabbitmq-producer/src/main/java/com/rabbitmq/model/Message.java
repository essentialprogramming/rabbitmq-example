package com.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    private String message;

    @JsonIgnore
    private Timestamp timestamp = Timestamp.from(Instant.now());
}
