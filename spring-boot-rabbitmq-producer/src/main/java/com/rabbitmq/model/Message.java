package com.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    private String message;
}
