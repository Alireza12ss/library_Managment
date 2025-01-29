package com.example.library.controller.user;

import com.example.library.dto.BookGroupDto;
import com.example.library.service.BookGroupService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user/book-groups")
@AllArgsConstructor
public class BookGroupController {

    private final BookGroupService bookGroupService;

    // Get all Book Groups
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookGroupDto>>> getAllBookGroups() {
        return ResponseEntity.ok(bookGroupService.getAllBookGroups());
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ApiResponse<List<BookGroupDto>>> searchBookGroups(@PathVariable String keyword) {
        return ResponseEntity.ok(bookGroupService.searchBookGroups(keyword));
    }
}
