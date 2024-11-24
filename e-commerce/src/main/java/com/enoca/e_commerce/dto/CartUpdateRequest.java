package com.enoca.e_commerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequest {

    private Long id;
    private Long productId;
    private int quantity;
}
