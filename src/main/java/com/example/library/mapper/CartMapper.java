package com.example.library.mapper;

import com.example.library.dto.CartDto;
import com.example.library.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    CartDto toDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Cart toEntity(CartDto cartDto);

    void partialUpdate(CartDto cartDto, @MappingTarget Cart cart);
}
