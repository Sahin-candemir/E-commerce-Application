package com.enoca.e_commerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private String description;
    private Double price;
    private int stock;
}
