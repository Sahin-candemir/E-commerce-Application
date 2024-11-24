package com.enoca.e_commerce.controller;

import com.enoca.e_commerce.dto.PlaceOrderRequest;
import com.enoca.e_commerce.dto.OrderResponse;
import com.enoca.e_commerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest){
        return new ResponseEntity<>(orderService.placeOrder(placeOrderRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<OrderResponse> getOrderForCode(@RequestParam("code") String code){
        return new ResponseEntity<>(orderService.getOrderForCode(code), HttpStatus.OK);
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForCustomer(@PathVariable Long customerId){
        return new ResponseEntity<>(orderService.getAllOrdersForCustomer(customerId), HttpStatus.OK);
    }
}
