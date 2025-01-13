package com.example.library.dto;

import com.example.library.entity.BookGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String title;
    private String author;
    private String group;
    private Long price;
}
