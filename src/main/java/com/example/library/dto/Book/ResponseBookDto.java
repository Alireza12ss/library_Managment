package com.example.library.dto.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookDto {
    private String title;
    private String author;
    private String group;
    private Long price;
}
