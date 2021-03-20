package com.spring.app.service;

import com.spring.app.dto.BookDTO;
import com.spring.app.entity.Book;
import com.spring.app.response.BookServiceResponse;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    BookServiceResponse findByISBN(final String iSBN);

    BookServiceResponse saveOrUpdateBook(final BookDTO bookDTO);

    BookServiceResponse deleteBook(final String isbn);

}