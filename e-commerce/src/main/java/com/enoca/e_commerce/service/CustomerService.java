package com.enoca.e_commerce.service;

import com.enoca.e_commerce.dto.CustomerRequest;
import com.enoca.e_commerce.dto.CustomerResponse;
import com.enoca.e_commerce.model.Customer;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest customerRequest);

    Customer getCustomer(Long id);
}
