package com.example.library.controller.admin;

import com.example.library.dto.BookRequestDto;
import com.example.library.entity.BookRequest;
import com.example.library.service.BookRequestService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/book-requests")
@AllArgsConstructor
public class AdBookRequestController {

    private final BookRequestService bookRequestService;

    @PutMapping("/{requestId}/status")
    public ResponseEntity<ApiResponse<Boolean>> updateBookRequestStatus(
            @PathVariable Long requestId,
            @RequestParam Boolean status) {
        return ResponseEntity.ok(bookRequestService.updateRequestStatus(requestId, status));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookRequestDto>>> getAll(){
        return ResponseEntity.ok(bookRequestService.getAllUserBookRequests());
    }
}
