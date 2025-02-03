package com.example.library.controller.user;

import com.example.library.dto.ResponseOrderDto;
import com.example.library.dto.ResultDto;
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
    public ResponseEntity<ResultDto<List<ResponseOrderDto>>> getUserOrders() {
        return ResponseEntity.ok(orderService.getUserOrders());

    }

    @PostMapping
    public ResponseEntity<ResultDto<ResponseOrderDto>> create() {
        return ResponseEntity.ok(orderService.create());
    }


}
