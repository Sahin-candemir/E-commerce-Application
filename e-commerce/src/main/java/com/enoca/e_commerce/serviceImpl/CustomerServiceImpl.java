package com.enoca.e_commerce.serviceImpl;

import com.enoca.e_commerce.dto.CustomerRequest;
import com.enoca.e_commerce.dto.CustomerResponse;
import com.enoca.e_commerce.exception.ResourceNotFoundException;
import com.enoca.e_commerce.model.Cart;
import com.enoca.e_commerce.model.Customer;
import com.enoca.e_commerce.repository.CustomerRepository;
import com.enoca.e_commerce.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        Cart cart = new Cart();

        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhoneNumber())
                .cart(cart)
                .build();

        Customer saveCustomer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .firstName(saveCustomer.getFirstName())
                .lastName(saveCustomer.getLastName())
                .email(saveCustomer.getEmail())
                .phoneNumber(saveCustomer.getPhoneNumber())
                .build();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id :"+id));
    }
}
