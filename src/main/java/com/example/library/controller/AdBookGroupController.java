package com.example.library.controller;

import com.example.library.dto.BookGroupDto;
import com.example.library.dto.BookGroupReqDto;
import com.example.library.service.BookGroupService;
import com.example.library.util.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<BookGroupDto>>> getAllBookGroups() {
        List<BookGroupDto> bookGroups = bookGroupService.getAllBookGroups();
        ApiResponse<List<BookGroupDto>> response = new ApiResponse<>("success", null, bookGroups);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookGroupDto>> addBookGroup(@RequestBody BookGroupReqDto bookGroupDto) {
        BookGroupDto createdBookGroup = bookGroupService.createBookGroup(bookGroupDto);
        ApiResponse<BookGroupDto> response = new ApiResponse<>("success", "Book group created successfully", createdBookGroup);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookGroupDto>> updateBookGroup(@PathVariable Long id, @RequestBody BookGroupDto bookGroupDto) {
        BookGroupDto updatedBookGroup = bookGroupService.updateBookGroup(id, bookGroupDto);
        ApiResponse<BookGroupDto> response = new ApiResponse<>("success", "Book group updated successfully", updatedBookGroup);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookGroup(@PathVariable Long id) {
        bookGroupService.deleteBookGroup(id);
        ApiResponse<Void> response = new ApiResponse<>("success", "Book group deleted successfully", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
