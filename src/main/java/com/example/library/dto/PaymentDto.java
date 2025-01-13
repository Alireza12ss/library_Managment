package com.example.library.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDto {
    private Long orderId;
    private Long amount;
    private Date paymentDate;
    private String status;
}