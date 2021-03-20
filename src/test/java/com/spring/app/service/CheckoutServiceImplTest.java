package com.spring.app.service;

import com.spring.app.constant.BookTypes;
import com.spring.app.constant.Messages;
import com.spring.app.dto.CheckoutServiceRequestDTO;
import com.spring.app.entity.Discount;
import com.spring.app.repository.DiscountRepository;
import com.spring.app.response.CheckoutServiceResponse;
import com.spring.app.util.CheckoutServiceResponseUtil;
import com.spring.app.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    private static final String PROMO_CODE_RAMADAN = "Ramadan-Discount";
    private static final String PROMO_CODE_EID = "Eid-Discount";
    private static boolean scaleSet = false;
    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private CheckoutServiceResponseUtil checkoutServiceResponseUtil;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    public static CheckoutServiceRequestDTO prepareRequestDTO() {
        return CheckoutServiceRequestDTO
                .builder()
                .books(TestUtil.prepareBooks())
                .promoCode(PROMO_CODE_RAMADAN)
                .build();
    }

    private static List<Discount> prepareDiscounts() {
        Discount discountRamadan = new Discount();
        discountRamadan.setPercentage(10.00);
        discountRamadan.setPromoCode(PROMO_CODE_RAMADAN);
        discountRamadan.setType(BookTypes.COMIC);
        discountRamadan.setValidity(LocalDate.parse("2022-06-30"));

        Discount discountEid = new Discount();
        discountEid.setPercentage(20.00);
        discountEid.setPromoCode(PROMO_CODE_EID);
        discountEid.setType(BookTypes.FANTASY);
        discountEid.setValidity(LocalDate.parse("2020-06-30"));

        return Arrays.asList(discountRamadan, discountEid);
    }

    @BeforeEach
    public void beforeEach() {
        if (!scaleSet) {
            checkoutService.setScale(3);
            scaleSet = true;
        }
    }

    @Test
    void testApplyPromotion() {
        when(discountRepository.findByPromoCode(anyString())).thenReturn(prepareDiscounts());
        when(checkoutServiceResponseUtil.prepareDiscountedResponse(any(BigDecimal.class), anyString(), anyString()))
                .thenReturn(CheckoutServiceResponse.builder().promoCode(PROMO_CODE_RAMADAN).build())
        ;

        CheckoutServiceRequestDTO request = prepareRequestDTO();
        CheckoutServiceResponse response = checkoutService.applyPromotion(request);

        assertNotNull(response);
        assertEquals(PROMO_CODE_RAMADAN, response.getPromoCode());
    }

    @Test
    void testApplyPromotionWhenNoPromotionIsThere() {
        when(discountRepository.findByPromoCode(anyString())).thenReturn(Collections.emptyList());
        when(checkoutServiceResponseUtil.prepareResponse(any(CheckoutServiceRequestDTO.class), anyString()))
                .thenReturn(CheckoutServiceResponse.builder().message(Messages.PROMO_CODE_NOT_FOUND.getValue()).build())
        ;

        CheckoutServiceResponse response = checkoutService.applyPromotion(CheckoutServiceRequestDTO.builder().promoCode(PROMO_CODE_RAMADAN).build());

        assertNotNull(response);
        assertEquals(Messages.PROMO_CODE_NOT_FOUND.getValue(), response.getMessage());
    }
}