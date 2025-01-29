package com.example.library.service;

import com.example.library.dto.OrderDto;
import com.example.library.entity.Order;
import com.example.library.mapper.OrderMapper;
import com.example.library.repository.CartRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.UserRepository;
import com.example.library.util.ApiResponse;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService extends SuperService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository, OrderMapper orderMapper) {
        super(userRepository);
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public ApiResponse<List<OrderDto>> getOrdersByUsername() {
        try {
            var username = getUsername();
            var orders = orderRepository.findByUserUsername(username)
                    .stream()
                    .map(orderMapper::toDto)
                    .toList();
            return ResponseUtil.success(orders);
        } catch (Exception e) {
            throw new CustomException.ServerError("Error while fetching orders: " + e.getMessage());
        }
    }

    public ApiResponse<OrderDto> getOrderById(Long orderId) {
        try {
            var order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException.NotFound("Order not found with ID: " + orderId));
            long totalPrice = order.getCart().getCartItems().stream()
                    .mapToLong(item -> item.getBook().getPrice() * item.getQuantity())
                    .sum();
            var orderDto = orderMapper.toDto(order);
            orderDto.setTotalPrice(totalPrice);
            return ResponseUtil.success(orderDto);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    @Transactional
    public ApiResponse<OrderDto> placeOrder() {
        try {
            var username = getUsername();
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException.NotFound("User not found with username: " + username));

            var cart = cartRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new CustomException.NotFound("Cart not found for user: " + username));

            if (cart.getCartItems().isEmpty()) {
                throw new CustomException.BadRequest("Cart is empty. Cannot place an order.");
            }
            var order = new Order();
            order.setUser(cart.getUser());
            order.setOrderDate(new Date());
            order.setTotalPrice(cart.getCartItems().stream()
                    .mapToLong(item -> item.getBook().getPrice() * item.getQuantity())
                    .sum());
            order.setPaymentCompleted(false);
            order.setCart(null);
            orderRepository.save(order);

            cart.getCartItems().clear();
            cartRepository.delete(cart);

            return ResponseUtil.success(orderMapper.toDto(order));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ApiResponse<List<OrderDto>> getAllOrders() {
        try {
            var orders = orderRepository.findAll()
                    .stream()
                    .map(orderMapper::toDto)
                    .toList();
            return ResponseUtil.success(orders);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ApiResponse<List<OrderDto>> getOrdersByUser(Long userId) {
        try {
            var orders = orderRepository.findByUserId(userId)
                    .stream()
                    .map(orderMapper::toDto)
                    .toList();
            return ResponseUtil.success(orders);
        } catch (Exception e) {
            throw new CustomException.ServerError("Error while fetching orders by user: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> updateOrderStatus(Long orderId, boolean isPaid) {
        try {
            var order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException.NotFound("Order not found with ID: " + orderId));
            order.setPaymentCompleted(isPaid);
            orderRepository.save(order);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
