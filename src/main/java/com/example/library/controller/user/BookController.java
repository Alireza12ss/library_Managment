package com.example.library.controller.user;

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
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(@PathVariable String title) {
        return ResponseEntity.ok(bookService.searchBooks(title));
    }
}
