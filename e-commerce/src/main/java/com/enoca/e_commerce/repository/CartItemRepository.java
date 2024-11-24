package com.enoca.e_commerce.repository;

import com.enoca.e_commerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);

    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId", nativeQuery = true)
    void deleteByCartIdNative(@Param("cartId") Long cartId);

    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId AND product_id = :productId", nativeQuery = true)
    void deleteByCartIdAndProductIdNative(@Param("cartId")Long cartId,@Param("productId") Long productId);
}
