package com.enoca.e_commerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest {

    @NotNull(message = "Cart id cannot be null")
    private Long cartId;

    @NotNull(message = "Customer id cannot be null")
    private Long customerId;
}
