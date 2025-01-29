package com.example.library.controller.user;

import com.example.library.dto.PaymentDto;
import com.example.library.service.PaymentService;
import com.example.library.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create a payment for an order
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<ApiResponse<PaymentDto>> makePayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.makePayment(orderId));
    }

    // Get all payments for the current user
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getUserPayments() {
        return ResponseEntity.ok(paymentService.getUserPayments());
    }
}
