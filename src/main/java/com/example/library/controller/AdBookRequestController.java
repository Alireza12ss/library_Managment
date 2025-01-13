package com.example.library.controller;

import com.example.library.service.BookRequestService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/book-requests")
@AllArgsConstructor
public class AdBookRequestController {

    private final BookRequestService bookRequestService;

    @PutMapping("/{requestId}/status")
    public ResponseEntity<ApiResponse<Void>> updateBookRequestStatus(
            @PathVariable Long requestId,
            @RequestParam String status) {
        bookRequestService.updateRequestStatus(requestId, status);
        ApiResponse<Void> response = new ApiResponse<>("success", "Book request status updated successfully", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
