package com.example.library.dto.Book;

import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookDto {
    private String title;
    private String author;
    private ResponseBookGroupDto group;
    private Long price;
}
