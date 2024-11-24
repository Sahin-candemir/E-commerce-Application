package com.enoca.e_commerce.service;

public interface CartItemService {

    void deleteCartItems(Long cartId);
    void deleteCartItemByProductId(Long cartId,Long productId);
}
