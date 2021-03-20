package com.spring.app.response;

import com.spring.app.entity.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BookServiceResponse extends Response {
    private Book book;
}