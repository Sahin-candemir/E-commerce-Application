package com.enoca.e_commerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private String name;
    private String description;
    private Double price;
    private int stock;
}
