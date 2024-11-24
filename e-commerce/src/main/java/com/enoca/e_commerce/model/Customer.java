package com.enoca.e_commerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();
}
