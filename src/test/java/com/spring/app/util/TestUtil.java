package com.spring.app.util;

import com.spring.app.constant.BookTypes;
import com.spring.app.dto.BookDTO;
import com.spring.app.entity.Book;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TestUtil {
    public static final String ISBN = "iSBN";

    public static List<Book> prepareBooks() {
        Book comicBook = new Book();
        comicBook.setId(1L);
        comicBook.setType(BookTypes.COMIC);
        comicBook.setName("Batman");
        comicBook.setPrice(BigDecimal.valueOf(100));

        Book fantasyBook = new Book();
        fantasyBook.setId(2L);
        fantasyBook.setName("Peaky Blinders");
        fantasyBook.setType(BookTypes.FANTASY);
        fantasyBook.setPrice(BigDecimal.valueOf(180));

        return Arrays.asList(comicBook, fantasyBook);
    }

    public static BookDTO prepareBookDTO() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setIsbn(ISBN);

        return bookDTO;
    }
}