package com.spring.app.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
public class CheckoutServiceResponse extends Response {
    private String promoCode;
    private boolean promoCodeApplied;
    private BigDecimal discountedPrice;

}