package com.example.library.controller.user;

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
        return ResponseEntity.ok(orderService.getOrdersByUsername());

    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> placeOrder() {
        return ResponseEntity.ok(orderService.placeOrder());
    }


}
