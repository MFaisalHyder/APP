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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookServiceResponseUtil bookServiceResponseUtil;
    private final ModelMapper modelMapper;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return CollectionUtils.isEmpty(books) ? new ArrayList<>() : books;
    }

    @Override
    public BookServiceResponse findByISBN(final String iSBN) {
        try {
            Book book = bookRepository.findByIsbn(iSBN);
            if (book == null)
                return bookServiceResponseUtil.prepareResponse(null, Messages.NO_BOOK_FOUND_ISBN.getValue());

            return bookServiceResponseUtil.prepareResponse(book, Messages.REQUEST_PROCESSED.getValue());

        } catch (Exception exception) {
            String message = bookServiceResponseUtil.getMessage(Messages.REQUEST_NOT_PROCESSED.getValue());
            throw new BookNotFoundException(message, exception.getMessage());
        }

    }

    @Override
    public BookServiceResponse saveOrUpdateBook(final BookDTO bookDTO) {
        boolean saveRequest = bookDTO.getId() == null;

        try {
            String message;

            Book book = bookRepository.findByIsbn(bookDTO.getIsbn());
            if (book == null) {
                book = modelMapper.map(bookDTO, Book.class);
                message = Messages.BOOK_SAVED.getValue();

            } else {
                book.setPrice(bookDTO.getPrice());
                book.setDescription(bookDTO.getDescription());
                message = Messages.BOOK_UPDATED.getValue();
            }

            book = bookRepository.save(book);

            return bookServiceResponseUtil.prepareResponse(book, message);
        } catch (Exception exception) {
            String message = saveRequest ? Messages.BOOK_NOT_SAVED.getValue() : Messages.BOOK_NOT_UPDATED.getValue();
            throw new BookNotSavedException(message, exception.getMessage());
        }
    }

    @Override
    public BookServiceResponse deleteBook(final String iSBN) {
        try {
            Book book = bookRepository.findByIsbn(iSBN);
            if (book == null)
                return bookServiceResponseUtil.prepareDeleteResponse(Messages.UNABLE_TO_DELETE_NO_BOOK_FOUND_ISBN.getValue(), false);

            bookRepository.deleteById(book.getId());

            return bookServiceResponseUtil.prepareDeleteResponse(Messages.REQUEST_PROCESSED.getValue(), true);

        } catch (Exception exception) {
            String message = bookServiceResponseUtil.getMessage(Messages.REQUEST_NOT_PROCESSED.getValue());
            throw new GeneralException(message, exception.getMessage());
        }
    }
}