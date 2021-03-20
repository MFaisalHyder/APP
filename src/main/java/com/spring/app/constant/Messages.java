package com.spring.app.constant;

public enum Messages {
    PARAMETER_MISSING("required.parameter.missing"),
    PARAMETER_MISSING_ISBN("required.parameter.missing.isbn"),
    NO_BOOK_FOUND_ISBN("book.not.found.isbn"),
    UNABLE_TO_DELETE_NO_BOOK_FOUND_ISBN("unable.to.delete.book.not.found.isbn"),
    BOOK_SAVED("book.saved"),
    BOOK_NOT_SAVED("book.not.saved"),
    BOOK_UPDATED("book.updated"),
    BOOK_NOT_UPDATED("book.not.updated"),
    REQUEST_NOT_PROCESSED("request.not.processed"),
    REQUEST_PROCESSED("request.processed"),
    PROMO_CODE_HAS_EXPIRED("promo.code.expired"),
    PROMO_CODE_APPLIED("promo.code.applied"),
    PROMO_CODE_NOT_APPLIED("promo.code.not.applied"),
    PROMO_CODE_NOT_FOUND("promo.code.not.found");

    private String value;

    Messages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}