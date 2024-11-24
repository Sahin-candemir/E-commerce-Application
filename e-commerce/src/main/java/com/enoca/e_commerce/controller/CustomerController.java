package com.enoca.e_commerce.controller;

import com.enoca.e_commerce.dto.CustomerRequest;
import com.enoca.e_commerce.dto.CustomerResponse;
import com.enoca.e_commerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        return new ResponseEntity<>(customerService.addCustomer(customerRequest), HttpStatus.CREATED);
    }
}
