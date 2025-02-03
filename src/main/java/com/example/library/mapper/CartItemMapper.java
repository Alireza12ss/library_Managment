package com.example.library.mapper;

import com.example.library.dto.CartItem.CartItemDto;
import com.example.library.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(CartItemDto cartItemDto);
}
