package com.spring.app.exception;

public class PromoCodeNotFoundException extends BaseException {

    public PromoCodeNotFoundException(String message) {
        super(message);
    }

    public PromoCodeNotFoundException(String message, String detail) {
        super(message, detail);
    }
}