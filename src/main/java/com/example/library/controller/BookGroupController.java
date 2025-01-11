package com.example.library.controller;

import com.example.library.dto.BookGroupDto;
import com.example.library.service.BookGroupService;
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
    public ResponseEntity<List<BookGroupDto>> getAllBookGroups() {
        List<BookGroupDto> groups = bookGroupService.getAllBookGroups();
        return ResponseEntity.ok(groups);
    }


    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BookGroupDto>> searchBookGroups(@PathVariable String keyword) {
        List<BookGroupDto> bookGroups = bookGroupService.searchBookGroups(keyword);
        return ResponseEntity.ok(bookGroups);
    }

}
