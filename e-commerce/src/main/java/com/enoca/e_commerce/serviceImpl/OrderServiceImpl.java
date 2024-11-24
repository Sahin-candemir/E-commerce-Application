package com.enoca.e_commerce.serviceImpl;

import com.enoca.e_commerce.dto.OrderItemDto;
import com.enoca.e_commerce.dto.PlaceOrderRequest;
import com.enoca.e_commerce.dto.OrderResponse;
import com.enoca.e_commerce.dto.ProductUpdateRequest;
import com.enoca.e_commerce.exception.EmptyCartException;
import com.enoca.e_commerce.exception.InsufficientStockException;
import com.enoca.e_commerce.exception.ResourceNotFoundException;
import com.enoca.e_commerce.model.*;
import com.enoca.e_commerce.repository.OrderRepository;
import com.enoca.e_commerce.service.CartService;
import com.enoca.e_commerce.service.CustomerService;
import com.enoca.e_commerce.service.OrderService;
import com.enoca.e_commerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;
    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, ProductService productService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
        Cart cart = cartService.getCart(placeOrderRequest.getCartId());
        checkCartIsEmpty(cart);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        Order order = new Order();
        for (CartItem cartItem : cart.getCartItems()){
            Product product = validateProductStock(cartItem);
            updateProductStock(cartItem, product);

            addOrderItemsAndOrderItemsDtoList(cartItem, product, order, orderItems, orderItemDtoList);
        }
        Customer customer = customerService.getCustomer(placeOrderRequest.getCustomerId());

        populateOrder(order, customer, cart, orderItems);
        Order savedOrder = orderRepository.save(order);
        cartService.emptyCart(cart.getId());
        return buildOrderResponse(savedOrder, orderItemDtoList);
    }

    @Override
    public OrderResponse getOrderForCode(String code) {
        Order order = orderRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with code : "+ code));
        List<OrderItemDto> orderItemDtoList = order.getOrderItems().stream().map(this::orderItemConverToOrderItemDto).toList();
        return buildOrderResponse(order, orderItemDtoList);
    }

    @Override
    public List<OrderResponse> getAllOrdersForCustomer(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream().map(this::orderConvertToOrderResponse).collect(Collectors.toList());
    }

    private OrderResponse orderConvertToOrderResponse(Order order) {
        List<OrderItemDto> orderItemDtoList = order.getOrderItems()
                .stream().map(this::orderItemConverToOrderItemDto).toList();
        return buildOrderResponse(order, orderItemDtoList);
    }

    private OrderItemDto orderItemConverToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productName(orderItem.getProduct().getName())
                .productDescription(orderItem.getProduct().getDescription())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    private static void checkCartIsEmpty(Cart cart) {
        if (cart.getCartItems().isEmpty()){
            throw new EmptyCartException("Your cart is empty, please add items to your cart.");
        }
    }

    private void addOrderItemsAndOrderItemsDtoList(CartItem cartItem, Product product, Order order, List<OrderItem> orderItems, List<OrderItemDto> orderItemDtoList) {
        orderItems.add(generateOrderItem(product, cartItem, order));
        orderItemDtoList.add(generateOrderItemDto(product, cartItem.getQuantity()));
    }

    private OrderResponse buildOrderResponse(Order savedOrder, List<OrderItemDto> orderItemDtoList) {
        return OrderResponse.builder()
                .code(savedOrder.getCode())
                .customerId(savedOrder.getCustomer().getId())
                .orderItemDtoList(orderItemDtoList)
                .build();
    }

    private void updateProductStock(CartItem cartItem, Product product) {
        int newStock = product.getStock()- cartItem.getQuantity();
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(product.getId(), product.getPrice(), newStock);
        productService.updateProduct(productUpdateRequest);
    }

    private Product validateProductStock(CartItem cartItem) {
        Product product = productService.getProduct(cartItem.getProduct().getId());
        if (product.getStock() < cartItem.getQuantity()){
            throw new InsufficientStockException(cartItem.getQuantity()+" units requested but only "+ product.getStock() +"units are available in stock.");
        }
        return product;
    }



    private OrderItemDto generateOrderItemDto(Product product, int quantity) {
        return OrderItemDto.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    private void populateOrder(Order order, Customer customer, Cart cart, List<OrderItem> orderItems) {
        order.setCustomer(customer);
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderItems(orderItems);
        order.setCode(generateOrderCode(customer.getId()));
    }

    private OrderItem generateOrderItem(Product product, CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

    private String generateOrderCode(Long customerId) {
        return customerId +"-"+ System.currentTimeMillis();
    }

}
