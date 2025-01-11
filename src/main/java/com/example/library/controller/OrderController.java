package com.example.library.controller;

import com.example.library.config.CustomUserDetails;
import com.example.library.dto.OrderDto;
import com.example.library.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    // Fetch orders by username
    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders() {
        String username = getAuthenticatedUsername();  // Extract username from token
        List<OrderDto> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

    // Place an order using username
    @PostMapping
    public ResponseEntity<OrderDto> placeOrder() {
        String username = getAuthenticatedUsername();  // Extract username from token
        OrderDto order = orderService.placeOrder(username);
        return ResponseEntity.ok(order);
    }

    // Fetch order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    private String getAuthenticatedUsername() {
        return getUsernameFromSecurityContext();
    }

    public String getUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        throw new IllegalStateException("Unable to retrieve username from SecurityContext");
    }
}
