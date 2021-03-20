package com.spring.app.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@ToString
public class Response implements Serializable {

    private boolean status;
    private String message;
    private String requestReceivedTime;
    private String requestProcessedTime;

}