package com.example.library.controller;

import com.example.library.dto.BookRequestDto;
import com.example.library.service.BookRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/book-requests")
public class BookRequestController {

    @Autowired
    private BookRequestService bookRequestService;

    @PostMapping
    public ResponseEntity<BookRequestDto> createBookRequest(@RequestBody BookRequestDto bookRequestDto, Principal principal) {
        // Use the username from the token
        String username = principal.getName(); // Extract username from the JWT token
        BookRequestDto createdRequest = bookRequestService.createBookRequest(bookRequestDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PatchMapping("/{id}/fulfill")
    public ResponseEntity<String> markRequestAsFulfilled(@PathVariable Long id) {
        bookRequestService.markRequestAsFulfilled(id);
        return ResponseEntity.ok("Book request marked as fulfilled.");
    }

    @GetMapping
    public ResponseEntity<List<BookRequestDto>> getAllRequests() {
        List<BookRequestDto> bookRequests = bookRequestService.getAllBookRequests();
        return ResponseEntity.ok(bookRequests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        bookRequestService.deleteBookRequest(id);
        return ResponseEntity.ok("Book request deleted successfully.");
    }
}
