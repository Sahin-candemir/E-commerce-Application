package com.enoca.e_commerce.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long customerId;
    private Set<CartItemDto> cartItemDtoSet;

    private Double totalAmount;
}
