package com.spring.app.util;

import com.spring.app.config.PropertiesConfig;
import com.spring.app.constant.Messages;
import com.spring.app.dto.CheckoutServiceRequestDTO;
import com.spring.app.entity.Book;
import com.spring.app.response.CheckoutServiceResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CheckoutServiceResponseUtil extends PropertiesConfigUtil {

    public CheckoutServiceResponseUtil(PropertiesConfig propertiesConfig) {
        super(propertiesConfig);
    }

    public CheckoutServiceResponse computeTotalWithoutPromoCode(CheckoutServiceRequestDTO checkoutServiceRequestDTO) {

        return checkoutServiceRequestDTO.isPromoCodeExpired()
                ? prepareResponse(checkoutServiceRequestDTO, Messages.PROMO_CODE_HAS_EXPIRED.getValue())
                : prepareResponse(checkoutServiceRequestDTO, Messages.PROMO_CODE_NOT_APPLIED.getValue());
    }

    public CheckoutServiceResponse prepareResponse(CheckoutServiceRequestDTO checkoutServiceRequestDTO,
                                                   final String message) {
        return CheckoutServiceResponse
                .builder()
                .status(true)
                .message(getMessage(message))
                .promoCode(checkoutServiceRequestDTO.getPromoCode())
                .promoCodeApplied(false)
                .discountedPrice(checkoutServiceRequestDTO.getBooks().stream().map(Book::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build()
                ;
    }

    public CheckoutServiceResponse prepareDiscountedResponse(BigDecimal discountedPrice,
                                                             final String promoCode, final String message) {
        return CheckoutServiceResponse
                .builder()
                .status(true)
                .message(getMessage(message))
                .promoCode(promoCode)
                .promoCodeApplied(true)
                .discountedPrice(discountedPrice)
                .build()
                ;
    }

}
