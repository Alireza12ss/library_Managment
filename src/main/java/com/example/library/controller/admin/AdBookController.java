package com.example.library.controller.admin;

import java.util.List;
import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.service.BookService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/books")
@AllArgsConstructor
public class AdBookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseBookDto>>> getList() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @PostMapping
    public ResponseEntity<ResultDto<ResponseBookDto>> create(@RequestBody CreateUpdateBookDto bookDto) {
        return ResponseEntity.ok(bookService.create(bookDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDto<ResponseBookDto>> update(@PathVariable Long id, @RequestBody CreateUpdateBookDto bookDto) {
        return ResponseEntity.ok(bookService.update(id, bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<ResultDto<List<ResponseBookDto>>> searchByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.search(title));
    }
}
