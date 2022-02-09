package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
public class Message {
    private String message;

    @JsonIgnore
    private Timestamp timestamp = Timestamp.from(Instant.now());
}
