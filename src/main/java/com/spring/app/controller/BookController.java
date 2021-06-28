package com.spring.app.controller;

import com.spring.app.config.PropertiesConfig;
import com.spring.app.constant.Messages;
import com.spring.app.dto.BookDTO;
import com.spring.app.entity.Book;
import com.spring.app.exception.InvalidInputException;
import com.spring.app.response.BookServiceResponse;
import com.spring.app.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final PropertiesConfig propertiesConfig;
    private final BookService bookServiceImpl;

    @ApiOperation(value = "View a list of available books")
    @GetMapping(value = "/books", produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookServiceImpl.getAllBooks(), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve a Book by its ISBN")
    @GetMapping(value = "/book/{iSBN}", produces = "application/json")
    public ResponseEntity<BookServiceResponse> getBookByIsbn(@PathVariable("iSBN") String iSBN) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (StringUtils.isBlank(iSBN))
            throw new InvalidInputException(propertiesConfig.getProperty(Messages.PARAMETER_MISSING_ISBN.getValue()));

        BookServiceResponse response = bookServiceImpl.findByISBN(iSBN);
        response.setRequestReceivedTime(localDateTime.toString());
        response.setRequestProcessedTime(LocalDateTime.now().toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Book by its ISBN")
    @DeleteMapping(value = "/book/{iSBN}", produces = "application/json")
    public ResponseEntity<BookServiceResponse> deleteBook(@PathVariable("iSBN") String iSBN) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (StringUtils.isBlank(iSBN))
            throw new InvalidInputException(propertiesConfig.getProperty(Messages.PARAMETER_MISSING_ISBN.getValue()));

        BookServiceResponse response = bookServiceImpl.deleteBook(iSBN);
        response.setRequestReceivedTime(localDateTime.toString());
        response.setRequestProcessedTime(LocalDateTime.now().toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Save a Book")
    @PostMapping(value = "/book", produces = "application/json")
    public ResponseEntity<BookServiceResponse> saveBook(@Valid @RequestBody BookDTO book) {
        LocalDateTime localDateTime = LocalDateTime.now();

        BookServiceResponse response = bookServiceImpl.saveOrUpdateBook(book);
        response.setRequestReceivedTime(localDateTime.toString());
        response.setRequestProcessedTime(LocalDateTime.now().toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getWithRequestBody")
    public ResponseEntity<List<String>> getAllUsers(@RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(Stream.of("Faisal", "Hyder", bookDTO.getName()).collect(Collectors.toList()), HttpStatus.OK);
    }
}