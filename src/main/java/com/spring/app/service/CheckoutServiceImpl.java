package com.spring.app.service;

import com.spring.app.constant.Messages;
import com.spring.app.dto.CheckoutServiceRequestDTO;
import com.spring.app.entity.Book;
import com.spring.app.entity.Discount;
import com.spring.app.repository.DiscountRepository;
import com.spring.app.response.CheckoutServiceResponse;
import com.spring.app.util.CheckoutServiceResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final DiscountRepository discountRepository;
    private final CheckoutServiceResponseUtil checkoutServiceResponseUtil;
    @Value("${rounding.scale.value}")
    @Setter
    private Integer scale;

    @Override
    public CheckoutServiceResponse applyPromotion(CheckoutServiceRequestDTO checkoutServiceRequestDTO) {
        List<Discount> discounts = discountRepository.findByPromoCode(checkoutServiceRequestDTO.getPromoCode());

        if (CollectionUtils.isEmpty(discounts))
            return checkoutServiceResponseUtil.prepareResponse(checkoutServiceRequestDTO, Messages.PROMO_CODE_NOT_FOUND.getValue());

        checkoutServiceRequestDTO.setDiscounts(discounts);

        return Optional
                .ofNullable(checkoutServiceRequestDTO.getPromoCode())
                .filter(isPromoCodeValid(checkoutServiceRequestDTO))
                .map(applyPromoDiscount(checkoutServiceRequestDTO))
                .orElse(checkoutServiceResponseUtil.computeTotalWithoutPromoCode(checkoutServiceRequestDTO))
                ;
    }

    private Predicate<String> isPromoCodeValid(CheckoutServiceRequestDTO checkoutServiceRequestDTO) {
        List<Discount> validDiscounts = getValidDiscounts().apply(checkoutServiceRequestDTO.getDiscounts());

        boolean noValidDiscountExist = CollectionUtils.isEmpty(validDiscounts);
        checkoutServiceRequestDTO.setPromoCodeExpired(noValidDiscountExist);
        checkoutServiceRequestDTO.setDiscounts(validDiscounts);

        return promoCode -> !noValidDiscountExist;
    }

    private UnaryOperator<List<Discount>> getValidDiscounts() {
        return discounts -> discounts
                .stream()
                .filter(Objects::nonNull)
                .filter(validityDateIsLessThanCurrentDate())
                .collect(Collectors.toList())
                ;
    }

    private Predicate<Discount> validityDateIsLessThanCurrentDate() {
        return discount -> discount.getValidity().compareTo(LocalDate.now()) >= 0;
    }

    private Function<String, CheckoutServiceResponse> applyPromoDiscount(CheckoutServiceRequestDTO checkoutServiceRequestDTO) {
        List<Discount> validDiscounts = checkoutServiceRequestDTO.getDiscounts();

        BigDecimal discountedPrice = checkoutServiceRequestDTO
                .getBooks()
                .stream()
                .map(performDiscountCalculation(validDiscounts))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ;

        return validPromoCode -> checkoutServiceResponseUtil.prepareDiscountedResponse(discountedPrice, checkoutServiceRequestDTO.getPromoCode(), Messages.PROMO_CODE_APPLIED.getValue());
    }

    private Function<Book, BigDecimal> performDiscountCalculation(List<Discount> validDiscounts) {
        return book -> validDiscounts
                .stream()
                .filter(discount -> discount.getType().equals(book.getType()))
                .findFirst()
                .map(updateBookPrice(book))
                .map(Book::getPrice)
                .orElse(book.getPrice())
                ;
    }

    private Function<Discount, Book> updateBookPrice(Book book) {

        return discount -> {
            BigDecimal discountPercentage = BigDecimal.valueOf(discount.getPercentage()).divide(BigDecimal.valueOf(100), scale, RoundingMode.CEILING);
            BigDecimal discountAmount = discountPercentage.multiply(book.getPrice());

            book.setPrice(book.getPrice().subtract(discountAmount));
            return book;
        };
    }
}