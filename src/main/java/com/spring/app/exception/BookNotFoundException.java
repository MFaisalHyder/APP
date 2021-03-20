package com.spring.app.exception;

public class BookNotFoundException extends BaseException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, String detail) {
        super(message, detail);
    }

}