package com.example.library.mapper;

import com.example.library.dto.ResponseCartDto;
import com.example.library.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    ResponseCartDto toDto(Cart cart);
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Cart toEntity(ResponseCartDto responseCartDto);
}
