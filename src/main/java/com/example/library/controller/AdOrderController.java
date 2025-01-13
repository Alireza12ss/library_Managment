package com.example.library.controller;

import com.example.library.dto.OrderDto;
import com.example.library.service.OrderService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/orders")
@AllArgsConstructor
public class AdOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        ApiResponse<List<OrderDto>> response = new ApiResponse<>("success", null, orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUser(userId);
        ApiResponse<List<OrderDto>> response = new ApiResponse<>("success", null, orders);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(@PathVariable Long orderId, @RequestParam boolean status) {
        orderService.updateOrderStatus(orderId, status);
        ApiResponse<Void> response = new ApiResponse<>("success", "Order status updated successfully", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
