package com.example.library.dto;

import com.example.library.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookGroupDto {
    private String name;
    private List<BookDto> books;
}

