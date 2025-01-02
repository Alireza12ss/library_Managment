package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book_requests")
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean fulfilled = false;
}
