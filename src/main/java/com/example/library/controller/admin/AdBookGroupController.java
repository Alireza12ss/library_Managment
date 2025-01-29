package com.example.library.controller.admin;

import com.example.library.dto.BookGroupDto;
import com.example.library.dto.BookGroupReqDto;
import com.example.library.service.BookGroupService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
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
        return ResponseEntity.ok(bookGroupService.getAllBookGroups());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookGroupDto>> addBookGroup(@RequestBody BookGroupReqDto bookGroupDto) {
        return ResponseEntity.ok(bookGroupService.createBookGroup(bookGroupDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookGroupDto>> updateBookGroup(@PathVariable Long id, @RequestBody BookGroupReqDto bookGroupDto) {
        return ResponseEntity.ok(bookGroupService.updateBookGroup(id, bookGroupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteBookGroup(@PathVariable Long id) {
        return ResponseEntity.ok(bookGroupService.deleteBookGroup(id));
    }

}
