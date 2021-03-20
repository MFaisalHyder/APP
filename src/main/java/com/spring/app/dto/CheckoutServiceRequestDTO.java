package com.spring.app.dto;

import com.spring.app.entity.Book;
import com.spring.app.entity.Discount;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CheckoutServiceRequestDTO {
    private List<Discount> discounts;
    private List<Book> books;
    private String promoCode;
    private boolean isPromoCodeExpired;
}