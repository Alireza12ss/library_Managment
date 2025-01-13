package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    private Status status; // "SUCCESS", "FAILED"
}