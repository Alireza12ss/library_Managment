package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.service.BookService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/books")
@AllArgsConstructor
public class AdBookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        ApiResponse<List<BookDto>> books = new ApiResponse<>(bookService.getAllBooks());
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> addBook(@RequestBody BookDto bookDto) {
        ApiResponse<BookDto> createdBook = new ApiResponse<>(bookService.addBook(bookDto));
        return ResponseEntity.status(201).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        ApiResponse<BookDto> updatedBook = new ApiResponse<>(bookService.updateBook(id, bookDto));
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        ApiResponse<Void> response = new ApiResponse<>(null, "Book deleted successfully");
        return ResponseEntity.ok(response);
    }
}
