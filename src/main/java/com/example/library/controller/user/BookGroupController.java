package com.example.library.controller.user;

import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.service.BookGroupService;
import com.example.library.dto.ResultDto;
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
    public ResponseEntity<ResultDto<List<ResponseBookGroupDto>>> getList() {
        return ResponseEntity.ok(bookGroupService.getAll());
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ResultDto<List<ResponseBookGroupDto>>> search(@PathVariable String keyword) {
        return ResponseEntity.ok(bookGroupService.search(keyword));
    }
}
