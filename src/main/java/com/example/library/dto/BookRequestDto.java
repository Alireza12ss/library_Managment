package com.example.library.dto;

import com.example.library.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class BookRequestDto {
    private String title;
    private String author;
    private boolean fulfilled;
}
