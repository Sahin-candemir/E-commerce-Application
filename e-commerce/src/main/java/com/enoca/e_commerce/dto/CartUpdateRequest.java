package com.enoca.e_commerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequest {

    @NotNull(message = "Cart id cannot be null")
    private Long id;

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
}
