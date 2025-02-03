package com.example.library.controller.admin;

import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.dto.BookGroup.CreateUpdateBookGroupDto;
import com.example.library.service.BookGroupService;
import com.example.library.dto.ResultDto;
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
    public ResponseEntity<ResultDto<List<ResponseBookGroupDto>>> getList() {
        return ResponseEntity.ok(bookGroupService.getAll());
    }

    @PostMapping
    public ResponseEntity<ResultDto<ResponseBookGroupDto>> create(@RequestBody CreateUpdateBookGroupDto bookGroupDto) {
        return ResponseEntity.ok(bookGroupService.create(bookGroupDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDto<ResponseBookGroupDto>> update(@PathVariable Long id, @RequestBody CreateUpdateBookGroupDto bookGroupDto) {
        return ResponseEntity.ok(bookGroupService.update(id, bookGroupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(bookGroupService.delete(id));
    }

}
