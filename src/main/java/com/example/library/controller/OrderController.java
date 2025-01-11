package com.example.library.controller;

import com.example.library.config.CustomUserDetails;
import com.example.library.dto.OrderDto;
import com.example.library.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/orders")
public class OrderController {


    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders() {
        List<OrderDto> orders = orderService.getOrdersByUsername();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder() {
        OrderDto order = orderService.placeOrder();
        return ResponseEntity.ok(order);
    }

    // Fetch order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }



}
