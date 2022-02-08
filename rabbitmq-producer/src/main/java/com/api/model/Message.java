package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private String message;

    @JsonIgnore
    private Timestamp timestamp = Timestamp.from(Instant.now());
}
