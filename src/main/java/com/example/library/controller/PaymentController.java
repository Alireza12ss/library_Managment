package com.example.library.controller;

import com.example.library.dto.PaymentDto;
import com.example.library.service.PaymentService;
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
    public ResponseEntity<PaymentDto> makePayment(@PathVariable Long orderId) {
        PaymentDto payment = paymentService.makePayment(orderId);
        return ResponseEntity.ok(payment);
    }

    // Get all payments for the current user
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getUserPayments() {
        List<PaymentDto> payments = paymentService.getUserPayments();
        return ResponseEntity.ok(payments);
    }
}
