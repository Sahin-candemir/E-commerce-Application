package com.enoca.e_commerce.controller;

import com.enoca.e_commerce.dto.CartResponse;
import com.enoca.e_commerce.dto.CartUpdateRequest;
import com.enoca.e_commerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id){
        return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<CartResponse> updateCart(@Valid @RequestBody CartUpdateRequest cartUpdateRequest){
        return new ResponseEntity<>(cartService.updateCart(cartUpdateRequest),HttpStatus.OK);
    }
    @PutMapping("/productAddFromCart")
    public ResponseEntity<CartResponse> addProductToCart(@Valid @RequestBody CartUpdateRequest cartUpdateRequest){
        return new ResponseEntity<>(cartService.addProductToCart(cartUpdateRequest),HttpStatus.OK);
    }
    @PutMapping("/removeProductFromCart")
    public ResponseEntity<CartResponse> removeProductFromCart(@Valid @RequestBody CartUpdateRequest cartUpdateRequest){
        return new ResponseEntity<>(cartService.removeProductFromCart(cartUpdateRequest),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> emptyCart(@PathVariable Long id){
        cartService.emptyCart(id);
        return new ResponseEntity<>("Cart cleared success.", HttpStatus.OK);
    }
}
