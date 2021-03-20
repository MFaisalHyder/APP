package com.spring.app.service;

import com.spring.app.dto.CheckoutServiceRequestDTO;
import com.spring.app.response.CheckoutServiceResponse;

public interface CheckoutService {

    CheckoutServiceResponse applyPromotion(CheckoutServiceRequestDTO checkoutServiceRequestDTO);
}