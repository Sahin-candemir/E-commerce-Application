package com.enoca.e_commerce.service;

import com.enoca.e_commerce.dto.CartResponse;
import com.enoca.e_commerce.dto.CartUpdateRequest;
import com.enoca.e_commerce.model.Cart;

public interface CartService {
    CartResponse getCartById(Long id);

    CartResponse updateCart(CartUpdateRequest cartUpdateRequest);

    void emptyCart(Long id);

    Cart getCart(Long id);

    CartResponse addProductToCart(CartUpdateRequest cartUpdateRequest);

    CartResponse removeProductFromCart(CartUpdateRequest cartUpdateRequest);
}
