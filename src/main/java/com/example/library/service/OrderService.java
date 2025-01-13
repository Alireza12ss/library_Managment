package com.example.library.service;

import com.example.library.dto.CartItemDto;
import com.example.library.dto.OrderDto;
import com.example.library.entity.Cart;
import com.example.library.entity.CartItem;
import com.example.library.entity.Order;
import com.example.library.exception.CartNotFoundException;
import com.example.library.exception.OrderNotFoundException;
import com.example.library.exception.UserNotFoundException;
import com.example.library.repository.CartRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService extends SuperService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(UserRepository userRepository, CartRepository cartRepository,
                        OrderRepository orderRepository, UserRepository userRepository1) {
        super(userRepository);
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository1;
    }

    // Fetch orders by username
    public List<OrderDto> getOrdersByUsername() {
        String username = getUsername();
        return orderRepository.findByUserUsername(username).stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    // Fetch order by ID
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        return mapToOrderDTO(order);
    }

    // Place an order using username
    public OrderDto placeOrder() {
        String username = getUsername();
        Cart cart = cartRepository.findByUserId(userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username))
                        .getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + username));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot place an order.");
        }

        // Create a new order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(new Date());
        order.setTotalPrice(cart.getCartItems().stream()
                .mapToLong(item -> item.getBook().getPrice() * item.getQuantity()).sum());
        order.setPaymentCompleted(false);

        // Move items from cart to order
        List<CartItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItem.setOrder(order); // Link to order
            orderItems.add(cartItem);
        }
        order.setItems(orderItems);
        orderRepository.save(order);
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return mapToOrderDTO(order);
    }

    // Map Order to OrderDto
    private OrderDto mapToOrderDTO(Order order) {
        return new OrderDto(
                order.getUser().getId(),
                order.getItems().stream()
                        .map(this::mapToCartItemDTO)
                        .collect(Collectors.toList()),
                new java.sql.Date(order.getOrderDate().getTime()), // Ensure correct type for SQL Date
                order.getTotalPrice(),
                order.isPaymentCompleted()
        );
    }

    // Map CartItem to CartItemDto
    private CartItemDto mapToCartItemDTO(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getQuantity(),
                cartItem.getBook().getPrice(),
                cartItem.getBook().getPrice() * cartItem.getQuantity()
        );
    }

    // Fetch all orders (optional)
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    // Fetch orders by user ID
    public List<OrderDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(Long orderId, boolean paid) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        order.setPaymentCompleted(paid);
        orderRepository.save(order);
    }
}
