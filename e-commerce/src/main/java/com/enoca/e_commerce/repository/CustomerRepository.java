package com.enoca.e_commerce.repository;

import com.enoca.e_commerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
