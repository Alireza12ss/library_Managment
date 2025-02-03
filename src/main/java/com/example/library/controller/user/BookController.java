package com.example.library.controller.user;

import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.ResultDto;
import com.example.library.dto.Book.CreateUpdateBookDto;
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
    public ResponseEntity<ResultDto<List<ResponseBookDto>>> getList() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDto<ResponseBookDto>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<ResultDto<List<ResponseBookDto>>> searchBooks(@PathVariable String title) {
        return ResponseEntity.ok(bookService.search(title));
    }
}
