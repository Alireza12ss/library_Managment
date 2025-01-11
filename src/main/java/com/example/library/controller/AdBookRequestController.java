package com.example.library.controller;

import com.example.library.service.BookRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/book-requests")
@AllArgsConstructor
public class AdBookRequestController {

    private final BookRequestService bookRequestService;

    @PutMapping("/{requestId}/status")
    public ResponseEntity<Void> updateBookRequestStatus(
            @PathVariable Long requestId,
            @RequestParam String status) {
        bookRequestService.updateRequestStatus(requestId, status);
        return ResponseEntity.noContent().build();
    }
}
