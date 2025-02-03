package com.example.library.controller.user;

import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.BookRequest.CreateBookRequestDto;
import com.example.library.dto.BookRequest.ResponseBookRequestDto;
import com.example.library.service.BookRequestService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user/book-requests")
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @PostMapping
    public ResponseEntity<ResultDto<ResponseBookRequestDto>> create(@RequestBody CreateBookRequestDto createBookRequestDto) {
        return ResponseEntity.ok(bookRequestService.create(createBookRequestDto));
    }

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseBookRequestDto>>> getAllRequests() {
        return ResponseEntity.ok(bookRequestService.getList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(bookRequestService.delete(id));
    }
}
