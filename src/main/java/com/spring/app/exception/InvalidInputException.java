package com.spring.app.exception;

public class InvalidInputException extends BaseException {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, String detail) {
        super(message, detail);
    }

}