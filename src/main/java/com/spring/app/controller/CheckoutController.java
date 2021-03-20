package com.spring.app.controller;

import com.spring.app.dto.CheckoutServiceRequestDTO;
import com.spring.app.request.CheckoutServiceRequest;
import com.spring.app.response.CheckoutServiceResponse;
import com.spring.app.service.CheckoutService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CheckoutController {

    private final CheckoutService checkoutServiceImpl;

    @ApiOperation(value = "Perform checkout and apply promo (if applicable)")
    @PostMapping(value = "/checkout", produces = "application/json")
    public ResponseEntity<CheckoutServiceResponse> checkout(@Valid @RequestBody CheckoutServiceRequest checkoutServiceRequest) {
        LocalDateTime localDateTime = LocalDateTime.now();

        CheckoutServiceResponse response = checkoutServiceImpl.applyPromotion(getRequestDto(checkoutServiceRequest));
        response.setRequestReceivedTime(localDateTime.toString());
        response.setRequestProcessedTime(LocalDateTime.now().toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private CheckoutServiceRequestDTO getRequestDto(CheckoutServiceRequest checkoutServiceRequest) {
        return CheckoutServiceRequestDTO
                .builder()
                .books(checkoutServiceRequest.getBooks())
                .promoCode(checkoutServiceRequest.getPromoCode())
                .build();
    }

}