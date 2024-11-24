package com.enoca.e_commerce.repository;

import com.enoca.e_commerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCode(String code);

    List<Order> findByCustomerId(Long customerId);
}
