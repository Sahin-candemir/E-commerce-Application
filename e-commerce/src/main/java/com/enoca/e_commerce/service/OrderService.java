package com.enoca.e_commerce.service;

import com.enoca.e_commerce.dto.PlaceOrderRequest;
import com.enoca.e_commerce.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(PlaceOrderRequest placeOrderRequest);

    OrderResponse getOrderForCode(String code);

    List<OrderResponse> getAllOrdersForCustomer(Long customerId);
}
