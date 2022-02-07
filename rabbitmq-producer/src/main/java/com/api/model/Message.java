package com.api.model;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private String message;
}
