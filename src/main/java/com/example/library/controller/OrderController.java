package com.example.library.controller;

import com.example.library.dto.OrderDto;
import com.example.library.util.ApiResponse;
import com.example.library.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getUserOrders() {
        List<OrderDto> orders = orderService.getOrdersByUsername();
        ApiResponse<List<OrderDto>> response = new ApiResponse<>("success", null, orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> placeOrder() {
        OrderDto order = orderService.placeOrder();
        ApiResponse<OrderDto> response = new ApiResponse<>("success", "Order placed successfully!", order);
        return ResponseEntity.ok(response);
    }

    // Fetch order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        ApiResponse<OrderDto> response = new ApiResponse<>("success", null, order);
        return ResponseEntity.ok(response);
    }
}
