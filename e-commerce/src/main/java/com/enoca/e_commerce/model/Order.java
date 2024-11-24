package com.enoca.e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_tb")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order extends BaseEntity{

    private Double totalAmount;
    private String code;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}
