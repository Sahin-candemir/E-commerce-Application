package com.enoca.e_commerce.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private String code;
    private Long customerId;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
}
