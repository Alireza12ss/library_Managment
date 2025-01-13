package com.example.library.service;
import com.example.library.dto.PaymentDto;
import com.example.library.entity.Order;
import com.example.library.entity.Payment;
import com.example.library.entity.Status;
import com.example.library.exception.OrderNotFoundException;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService extends SuperService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        super(null);
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // Create a fake payment for an order
    @Transactional
    public PaymentDto makePayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.isPaymentCompleted()) {
            throw new IllegalArgumentException("Payment already completed for this order");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(new Date());
        payment.setStatus(Status.SUCCESS);

        paymentRepository.save(payment);

        order.setPaymentCompleted(true);
        orderRepository.save(order);

        return mapToPaymentDto(payment);
    }

    // Fetch all payments for the current user
    public List<PaymentDto> getUserPayments() {
        Long userId = getCurrentUserId();
        return paymentRepository.findByOrderUserId(userId).stream()
                .map(this::mapToPaymentDto)
                .collect(Collectors.toList());
    }

    public List<PaymentDto> getUserPayments(Long userId) {
        return paymentRepository.findByOrderUserId(userId).stream()
                .map(this::mapToPaymentDto)
                .collect(Collectors.toList());
    }

    // Map Payment to PaymentDto
    private PaymentDto mapToPaymentDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setOrderId(payment.getOrder().getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setStatus(String.valueOf(payment.getStatus()));
        return dto;
    }
}