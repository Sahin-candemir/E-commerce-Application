package com.enoca.e_commerce.serviceImpl;

import com.enoca.e_commerce.dto.CartItemDto;
import com.enoca.e_commerce.dto.CartResponse;
import com.enoca.e_commerce.dto.CartUpdateRequest;
import com.enoca.e_commerce.dto.ProductResponse;
import com.enoca.e_commerce.exception.ProductAlreadyExistsInCartException;
import com.enoca.e_commerce.exception.ResourceNotFoundException;
import com.enoca.e_commerce.model.Cart;
import com.enoca.e_commerce.model.CartItem;
import com.enoca.e_commerce.model.Product;
import com.enoca.e_commerce.repository.CartRepository;
import com.enoca.e_commerce.service.CartItemService;
import com.enoca.e_commerce.service.CartService;
import com.enoca.e_commerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemService cartItemService;
    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @Override
    public CartResponse getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id : "+id));
        return cartMapToCartResponse(cart);
    }

    @Override
    public CartResponse updateCart(CartUpdateRequest cartUpdateRequest) {
        Cart cart = cartRepository.findById(cartUpdateRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id : "+cartUpdateRequest.getId()));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(cartUpdateRequest.getQuantity());
        cartItem.setProduct(productService.getProduct(cartUpdateRequest.getProductId()));

        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        Cart savedCart = cartRepository.save(cart);

        return cartMapToCartResponse(savedCart);
    }

    @Override
    public void emptyCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id : "+id));
        cartItemService.deleteCartItems(id);
    }

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id : "+id));
    }

    @Override
    public CartResponse addProductToCart(CartUpdateRequest cartUpdateRequest) {
        Cart cart = cartRepository.findById(cartUpdateRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id : "+cartUpdateRequest.getId()));
        Product product = productService.getProduct(cartUpdateRequest.getProductId());
        boolean productExistsInCart = cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProduct().getId().equals(cartUpdateRequest.getProductId()));
        if (productExistsInCart){
            throw new ProductAlreadyExistsInCartException("Product is already in the cart.");
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(cartUpdateRequest.getQuantity());
        cartItem.setProduct(productService.getProduct(cartUpdateRequest.getProductId()));

        Set<CartItem> cartItems = cart.getCartItems();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        Cart updatedCart = cartRepository.save(cart);

        return cartMapToCartResponse(updatedCart);
    }

    @Override
    public CartResponse removeProductFromCart(CartUpdateRequest cartUpdateRequest) {
        Cart cart = cartRepository.findById(cartUpdateRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id : "+cartUpdateRequest.getId()));
        Product product = productService.getProduct(cartUpdateRequest.getProductId());
        boolean productExistsInCart = cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProduct().getId().equals(cartUpdateRequest.getProductId()));
        if (!productExistsInCart){
            throw new ResourceNotFoundException("Product not found in the cart.");
        }
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(cartUpdateRequest.getProductId()));
        cartItemService.deleteCartItemByProductId(cart.getId(), product.getId());
        //Cart updatedCart = cartRepository.save(cart);
        return cartMapToCartResponse(cart);
    }

    private CartResponse cartMapToCartResponse(Cart cart) {

        Set<CartItemDto> cartItemDtoSet = cart.getCartItems().stream().map(cartItem -> {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setQuantity(cartItem.getQuantity());

            ProductResponse productResponse = productMapToProductResponse(cartItem.getProduct());
            cartItemDto.setProductResponse(productResponse);
            return cartItemDto;
        }).collect(Collectors.toSet());
        return CartResponse.builder()
                .totalAmount(cart.getTotalPrice())
                .customerId(cart.getCustomer().getId())
                .cartItemDtoSet(cartItemDtoSet)
                .build();
    }

    private ProductResponse productMapToProductResponse(Product product) {

        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
