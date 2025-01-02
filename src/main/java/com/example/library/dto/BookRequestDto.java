package com.example.library.dto;

import com.example.library.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class BookRequestDto {
    private Long id;
    private String title;
    private String author;
    private User user;
    private boolean fulfilled;
}
