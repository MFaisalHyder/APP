package com.spring.app.exception;

public class GeneralException extends BaseException {

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, String detail) {
        super(message, detail);
    }

}