package com.example.library.service;

import com.example.library.dto.PaymentDto;
import com.example.library.entity.Payment;
import com.example.library.entity.Status;
import com.example.library.mapper.PaymentMapper;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.PaymentRepository;
import com.example.library.repository.UserRepository;
import com.example.library.dto.ResultDto;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService extends SuperService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository, OrderRepository orderRepository, PaymentMapper paymentMapper) {
        super(userRepository);
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentMapper = paymentMapper;
    }

    @Transactional
    public ResultDto<PaymentDto> create(Long orderId) {
        try {
            var order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException.NotFound("Order not found with ID: " + orderId));

            if (order.isPaymentCompleted()) {
                throw new CustomException.BadRequest("Payment already completed for this order");
            }

            var payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotalPrice());
            payment.setPaymentDate(new Date());
            payment.setStatus(Status.SUCCESS);

            paymentRepository.save(payment);

            order.setPaymentCompleted(true);
            orderRepository.save(order);

            return ResponseUtil.success(paymentMapper.toDto(payment));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<PaymentDto>> getList() {
        try {
            var userId = getCurrentUserId();
            var payments = paymentRepository.findByOrderUserId(userId).stream()
                    .map(paymentMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(payments);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<PaymentDto>> getUserPayments(Long userId) {
        try {
            var payments = paymentRepository.findByOrderUserId(userId).stream()
                    .map(paymentMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseUtil.success(payments);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
