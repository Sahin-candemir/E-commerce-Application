package com.enoca.e_commerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private String productName;

    private String productDescription;

    private Integer quantity;

    private Double price;
}
