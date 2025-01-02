package com.example.library.dto;

import com.example.library.entity.BookGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private String Author;
    private String group;
}
