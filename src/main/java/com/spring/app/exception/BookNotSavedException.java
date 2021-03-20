package com.spring.app.exception;

public class BookNotSavedException extends BaseException {

    public BookNotSavedException(String message) {
        super(message);
    }

    public BookNotSavedException(String message, String detail) {
        super(message, detail);
    }

}