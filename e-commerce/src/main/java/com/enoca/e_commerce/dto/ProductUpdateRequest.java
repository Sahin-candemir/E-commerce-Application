package com.enoca.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    private Long id;
    private Double price;
    private Integer stock;
}
