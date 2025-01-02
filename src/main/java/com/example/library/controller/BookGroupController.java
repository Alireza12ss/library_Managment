package com.example.library.controller;

import com.example.library.dto.BookGroupDto;
import com.example.library.entity.BookGroup;
import com.example.library.service.BookGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-groups")
public class BookGroupController {

    private final BookGroupService bookGroupService;

    public BookGroupController(BookGroupService bookGroupService) {
        this.bookGroupService = bookGroupService;
    }

    // Create a new Book Group
    @PostMapping
    public ResponseEntity<BookGroup> createBookGroup(@RequestBody BookGroupDto bookGroupDto) {
        BookGroup createdGroup = bookGroupService.createBookGroup(bookGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    // Get all Book Groups
    @GetMapping
    public ResponseEntity<List<BookGroup>> getAllBookGroups() {
        List<BookGroup> groups = bookGroupService.getAllBookGroups();
        return ResponseEntity.ok(groups);
    }

    // Update a Book Group
    @PutMapping("/{id}")
    public ResponseEntity<BookGroup> updateBookGroup(@PathVariable Long id, @RequestBody BookGroupDto bookGroupDto) {
        BookGroup updatedGroup = bookGroupService.updateBookGroup(id, bookGroupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    // Delete a Book Group
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookGroup(@PathVariable Long id) {
        bookGroupService.deleteBookGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BookGroupDto>> searchBookGroups(@PathVariable String keyword) {
        List<BookGroupDto> bookGroups = bookGroupService.searchBookGroups(keyword);
        return ResponseEntity.ok(bookGroups);
    }

}
