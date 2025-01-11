package com.example.library.controller;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookRequestResponse;
import com.example.library.service.BookRequestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user/book-requests")
public class BookRequestController {

    private BookRequestService bookRequestService;

    @PostMapping
    public ResponseEntity<BookRequestResponse> createBookRequest(@RequestBody BookRequestDto bookRequestDto, Principal principal) {
        String username = principal.getName();
        BookRequestResponse createdRequest = bookRequestService.createBookRequest(bookRequestDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping
    public ResponseEntity<List<BookRequestDto>> getAllRequests(Principal principal) {
        String username = principal.getName();
        List<BookRequestDto> bookRequests = bookRequestService.getAllBookRequests(username);
        return ResponseEntity.ok(bookRequests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        bookRequestService.deleteBookRequest(id);
        return ResponseEntity.ok("Book request deleted successfully.");
    }
}
