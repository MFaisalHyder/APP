package com.spring.app.service;

import com.spring.app.constant.Messages;
import com.spring.app.dto.BookDTO;
import com.spring.app.entity.Book;
import com.spring.app.exception.BookNotFoundException;
import com.spring.app.exception.BookNotSavedException;
import com.spring.app.exception.GeneralException;
import com.spring.app.repository.BookRepository;
import com.spring.app.response.BookServiceResponse;
import com.spring.app.util.BookServiceResponseUtil;
import com.spring.app.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static com.spring.app.util.TestUtil.ISBN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookServiceResponseUtil bookServiceResponseUtil;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(TestUtil.prepareBooks());

        List<Book> response = bookService.getAllBooks();

        assertNotNull(response);
    }

    @Test
    void testGetAllBooksEmpty() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(bookService.getAllBooks().isEmpty());
    }

    @Test
    void testFindByISBN() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(TestUtil.prepareBooks().get(0));
        when(bookServiceResponseUtil.prepareResponse(any(Book.class), anyString()))
                .thenReturn(BookServiceResponse.builder().message(Messages.REQUEST_PROCESSED.getValue()).build())
        ;

        BookServiceResponse response = bookService.findByISBN(ISBN);

        assertNotNull(response);
        assertEquals(Messages.REQUEST_PROCESSED.getValue(), response.getMessage());
    }

    @Test
    void testFindByISBNNoBookFound() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(null);
        when(bookServiceResponseUtil.prepareResponse(isNull(), anyString()))
                .thenReturn(BookServiceResponse.builder().message(Messages.NO_BOOK_FOUND_ISBN.getValue()).build())
        ;

        BookServiceResponse response = bookService.findByISBN(ISBN);

        assertNotNull(response);
        assertEquals(Messages.NO_BOOK_FOUND_ISBN.getValue(), response.getMessage());
    }

    @Test
    void testFindByISBNException() {
        doThrow(new RuntimeException()).when(bookRepository).findByIsbn(anyString());

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.findByISBN("isBN"));
    }

    @Test
    void testSaveOrUpdateBook() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(null);
        when(modelMapper.map(any(), any())).thenReturn(new Book());
        when(bookRepository.save(any(Book.class))).thenReturn(TestUtil.prepareBooks().get(0));
        when(bookServiceResponseUtil.prepareResponse(any(Book.class), anyString())).thenReturn(BookServiceResponse.builder().message(Messages.BOOK_SAVED.getValue()).build());

        BookDTO bookDTO = TestUtil.prepareBookDTO();

        BookServiceResponse response = bookService.saveOrUpdateBook(bookDTO);

        assertNotNull(response);
        assertEquals(Messages.BOOK_SAVED.getValue(), response.getMessage());
        verify(bookRepository, times(1)).findByIsbn(anyString());
        verify(modelMapper, times(1)).map(any(), any());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testSaveOrUpdateBookException() {
        doThrow(new RuntimeException()).when(bookRepository).findByIsbn(anyString());

        BookDTO bookDTO = TestUtil.prepareBookDTO();
        Assertions.assertThrows(BookNotSavedException.class, () -> bookService.saveOrUpdateBook(bookDTO));
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(TestUtil.prepareBooks().get(0));
        doNothing().when(bookRepository).deleteById(anyLong());
        when(bookServiceResponseUtil.prepareDeleteResponse(anyString(), anyBoolean()))
                .thenReturn(BookServiceResponse.builder().message(Messages.REQUEST_PROCESSED.getValue()).build())
        ;

        BookServiceResponse response = bookService.deleteBook(ISBN);

        assertNotNull(response);
        assertEquals(Messages.REQUEST_PROCESSED.getValue(), response.getMessage());
        verifyMockUsage();
    }

    @Test
    void testDeleteBookNoBookFound() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(null);
        when(bookServiceResponseUtil.prepareDeleteResponse(anyString(), anyBoolean()))
                .thenReturn(BookServiceResponse.builder().message(Messages.UNABLE_TO_DELETE_NO_BOOK_FOUND_ISBN.getValue()).build())
        ;

        BookServiceResponse response = bookService.deleteBook(ISBN);

        assertNotNull(response);
        assertEquals(Messages.UNABLE_TO_DELETE_NO_BOOK_FOUND_ISBN.getValue(), response.getMessage());
        verifyMockUsage();
    }

    @Test
    void testDeleteBookException() {
        doThrow(new RuntimeException()).when(bookRepository).findByIsbn(anyString());

        Assertions.assertThrows(GeneralException.class, () -> bookService.deleteBook("isBN"));
    }

    private void verifyMockUsage() {
        verify(bookRepository, times(1)).findByIsbn(anyString());
        verify(bookServiceResponseUtil, times(1)).prepareDeleteResponse(anyString(), anyBoolean());
    }

}