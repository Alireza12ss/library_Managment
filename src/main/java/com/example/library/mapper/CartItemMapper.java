package com.example.library.mapper;

import com.example.library.dto.CartItem.CartItemDto;
import com.example.library.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.price", target = "pricePerUnit")
    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(CartItemDto cartItemDto);
}
