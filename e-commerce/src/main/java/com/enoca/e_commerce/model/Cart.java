package com.enoca.e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cart extends BaseEntity{

    @OneToOne(mappedBy = "cart")
    private Customer customer;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    public double getTotalPrice(){
        return cartItems.stream().mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity()).sum();
    }
}
