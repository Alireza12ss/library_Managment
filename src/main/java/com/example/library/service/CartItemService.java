package com.example.library.service;

import com.example.library.dto.CartItem.CartItemDto;
import com.example.library.entity.CartItem;
import com.example.library.mapper.CartItemMapper;
import com.example.library.repository.CartItemRepository;
import com.example.library.dto.ResultDto;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public ResultDto<CartItemDto> getById(Long id) {
        try {
            CartItem cartItem = cartItemRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("CartItem not found with ID: " + id));
            return ResponseUtil.success(cartItemMapper.toDto(cartItem));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
