package com.spring.app.util;

import com.spring.app.config.PropertiesConfig;
import com.spring.app.entity.Book;
import com.spring.app.response.BookServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class BookServiceResponseUtil extends PropertiesConfigUtil {

    public BookServiceResponseUtil(PropertiesConfig propertiesConfig) {
        super(propertiesConfig);
    }

    public BookServiceResponse prepareResponse(Book book, final String message) {

        return BookServiceResponse
                .builder()
                .status(true)
                .message(getMessage(message))
                .book(book)
                .build();
    }

    public BookServiceResponse prepareDeleteResponse(final String message, final boolean status) {
        return BookServiceResponse
                .builder()
                .status(status)
                .message(getMessage(message))
                .build();
    }

}