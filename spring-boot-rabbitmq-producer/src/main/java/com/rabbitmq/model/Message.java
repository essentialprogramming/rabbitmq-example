package com.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;

import java.io.Serializable;

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
