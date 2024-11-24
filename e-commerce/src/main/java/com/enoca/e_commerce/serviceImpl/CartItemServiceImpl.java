package com.enoca.e_commerce.serviceImpl;

import com.enoca.e_commerce.model.CartItem;
import com.enoca.e_commerce.repository.CartItemRepository;
import com.enoca.e_commerce.service.CartItemService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    @Transactional
    public void deleteCartItems(Long cartId) {
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
        cartItemRepository.deleteByCartIdNative(cartId);
    }

    @Transactional
    @Override
    public void deleteCartItemByProductId(Long cartId, Long productId) {
        cartItemRepository.deleteByCartIdAndProductIdNative(cartId,productId);
    }

}
