package com.example.library.controller.admin;

import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.service.AuthService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/users")
@AllArgsConstructor
public class AdUserController {

    private final AuthService userService;

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseUserDto>>> getList() {
        return ResponseEntity.ok(userService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDto<ResponseUserDto>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
