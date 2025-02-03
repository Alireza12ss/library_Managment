package com.example.library.controller.admin;

import com.example.library.dto.PaymentDto;
import com.example.library.service.PaymentService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/admin/payments")
public class AdPaymentController {
    private final PaymentService paymentService;


    @GetMapping("/{userId}")
    public ResponseEntity<ResultDto<List<PaymentDto>>> getUserPayments(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getUserPayments(userId));
    }


}
