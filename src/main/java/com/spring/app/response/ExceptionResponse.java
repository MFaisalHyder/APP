package com.spring.app.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class ExceptionResponse implements Serializable {

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private String details;

    public static ExceptionResponse prepareErrorResponse(HttpStatus status, String errorCode, String errorMessage) {

        return new ResponseBuilder()
                .setStatus(status)
                .setCode(errorCode)
                .setMessage(errorMessage)
                .build();
    }

    public static final class ResponseBuilder {
        private HttpStatus httpStatus;
        private String code;
        private String message;
        private String details;

        public ResponseBuilder() {
        }

        public ResponseBuilder setStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;

            return this;
        }

        public ResponseBuilder setCode(String code) {
            this.code = code;

            return this;
        }

        public ResponseBuilder setMessage(String message) {
            this.message = message;

            return this;
        }

        public ResponseBuilder setDetails(String details) {
            this.details = details;

            return this;
        }

        public ExceptionResponse build() {
            ExceptionResponse exceptionResponse = new ExceptionResponse();

            exceptionResponse.httpStatus = this.httpStatus;
            exceptionResponse.errorCode = this.code;
            exceptionResponse.message = this.message;
            exceptionResponse.details = this.details;

            return exceptionResponse;
        }

    }

}