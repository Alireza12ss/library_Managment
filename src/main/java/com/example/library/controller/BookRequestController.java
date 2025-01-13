package com.example.library.controller;

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
    public ResponseEntity<ApiResponse<BookRequestResponse>> createBookRequest(@RequestBody BookRequestDto bookRequestDto) {
        BookRequestResponse createdRequest = bookRequestService.createBookRequest(bookRequestDto);
        ApiResponse<BookRequestResponse> response = new ApiResponse<>("success", "Book request created successfully", createdRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookRequestDto>>> getAllRequests() {
        List<BookRequestDto> bookRequests = bookRequestService.getAllBookRequests();
        ApiResponse<List<BookRequestDto>> response = new ApiResponse<>("success", null, bookRequests);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRequest(@PathVariable Long id) {
        bookRequestService.deleteBookRequest(id);
        ApiResponse<String> response = new ApiResponse<>("success", "Book request deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
