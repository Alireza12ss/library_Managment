package com.example.library.controller;

import com.example.library.util.ApiResponse;
import com.example.library.dto.BookDto;
import com.example.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        ApiResponse<List<BookDto>> response = new ApiResponse<>("success", null, books);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        ApiResponse<BookDto> response = new ApiResponse<>("success", null, book);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(@PathVariable String title) {
        List<BookDto> filteredBooks = bookService.searchBooks(title);
        ApiResponse<List<BookDto>> response = new ApiResponse<>("success", null, filteredBooks);
        return ResponseEntity.ok(response);
    }
}
