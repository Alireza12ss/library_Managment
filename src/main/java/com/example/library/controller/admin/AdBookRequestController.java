package com.example.library.controller.admin;

import com.example.library.dto.BookRequest.CreateBookRequestDto;
import com.example.library.dto.BookRequest.ResponseBookRequestDto;
import com.example.library.service.BookRequestService;
import com.example.library.dto.ResultDto;
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
    public ResponseEntity<ResultDto<Boolean>> update(
            @PathVariable Long requestId,
            @RequestParam Boolean status) {
        return ResponseEntity.ok(bookRequestService.update(requestId, status));

    }

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseBookRequestDto>>> getAll(){
        return ResponseEntity.ok(bookRequestService.getListAdmin());
    }
}
