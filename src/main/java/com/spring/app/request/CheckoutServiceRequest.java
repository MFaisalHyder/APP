package com.spring.app.request;

import com.spring.app.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CheckoutServiceRequest {
    @NotEmpty(message = "At least one book is required to perform checkout")
    private List<Book> books;

    private String promoCode;
}