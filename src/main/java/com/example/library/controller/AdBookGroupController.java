package com.example.library.controller;

import com.example.library.dto.BookGroupDto;
import com.example.library.service.BookGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/book-groups")
@AllArgsConstructor
public class AdBookGroupController {

    private final BookGroupService bookGroupService;

    @GetMapping
    public List<BookGroupDto> getAllBookGroups() {
        return bookGroupService.getAllBookGroups();
    }

    @PostMapping
    public ResponseEntity<BookGroupDto> addBookGroup(@RequestBody BookGroupDto bookGroupDto) {
        return new ResponseEntity<>(bookGroupService.createBookGroup(bookGroupDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookGroupDto> updateBookGroup(@PathVariable Long id, @RequestBody BookGroupDto bookGroupDto) {
        return ResponseEntity.ok(bookGroupService.updateBookGroup(id, bookGroupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookGroup(@PathVariable Long id) {
        bookGroupService.deleteBookGroup(id);
        return ResponseEntity.noContent().build();
    }
}
