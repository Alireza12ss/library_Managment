package com.example.library.controller.user;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookRequestResponse;
import com.example.library.service.BookRequestService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user/book-requests")
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookRequestDto>> createBookRequest(@RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookRequestService.createBookRequest(bookRequestDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookRequestDto>>> getAllRequests() {
        return ResponseEntity.ok(bookRequestService.getAllBookRequests());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteRequest(@PathVariable Long id) {
        return ResponseEntity.ok(bookRequestService.deleteBookRequest(id));
    }
}
