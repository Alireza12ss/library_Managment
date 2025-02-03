package com.example.library.controller.admin;

import com.example.library.dto.ResponseOrderDto;
import com.example.library.service.OrderService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/orders")
@AllArgsConstructor
public class AdOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseOrderDto>>> getList() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResultDto<List<ResponseOrderDto>>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrdersForAdmin(userId));
    }

    // Fetch order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<ResultDto<ResponseOrderDto>> getById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }
}
