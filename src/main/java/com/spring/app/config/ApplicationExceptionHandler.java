package com.spring.app.config;

import com.spring.app.constant.ErrorCodes;
import com.spring.app.exception.BookNotFoundException;
import com.spring.app.exception.BookNotSavedException;
import com.spring.app.exception.GeneralException;
import com.spring.app.exception.InvalidInputException;
import com.spring.app.response.ExceptionResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

/*    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void defaultExceptionHandler() {

    }*/

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionResponse> bookNotFoundExceptionHandler(BookNotFoundException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.NOT_FOUND)
                .setCode(ErrorCodes.ZERO_RECORD.getValue())
                .setMessage(exception.getMessage())
                .setDetails(StringUtils.isBlank(exception.getDetail()) ? exception.getMessage() : exception.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(BookNotSavedException.class)
    public ResponseEntity<ExceptionResponse> bookNotSavedExceptionHandler(BookNotSavedException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setCode(ErrorCodes.RECORD_NOT_SAVED.getValue())
                .setMessage(exception.getMessage())
                .setDetails(StringUtils.isBlank(exception.getDetail()) ? exception.getMessage() : exception.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionResponse> invalidInputExceptionHandler(InvalidInputException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setCode(ErrorCodes.INVALID_PARAMETER.getValue())
                .setMessage(exception.getMessage())
                .setDetails(StringUtils.isBlank(exception.getDetail()) ? exception.getMessage() : exception.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }


    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ExceptionResponse> generalExceptionHandler(GeneralException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse.ResponseBuilder()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setCode(ErrorCodes.GENERAL_ERROR.getValue())
                .setMessage(exception.getMessage())
                .setDetails(StringUtils.isBlank(exception.getDetail()) ? exception.getMessage() : exception.getDetail())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())
                ;

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

}