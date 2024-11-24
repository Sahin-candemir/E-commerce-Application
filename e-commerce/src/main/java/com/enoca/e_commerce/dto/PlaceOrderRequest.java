package com.enoca.e_commerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest {

    private Long cartId;
    private Long customerId;
}
