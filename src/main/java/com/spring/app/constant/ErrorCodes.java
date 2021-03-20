package com.spring.app.constant;

public enum ErrorCodes {
    ZERO_RECORD("ZERO_RECORD"),
    RECORD_NOT_SAVED("501-RECORD_NOT_SAVED"),
    INVALID_PARAMETER("INVALID_PARAMETER"),
    GENERAL_ERROR("500-GENERAL_ERROR");

    private String value;

    ErrorCodes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}