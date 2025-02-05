package com.example.library.mapper;

import com.example.library.dto.WishlistDto;
import com.example.library.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    WishlistDto toDto(Wishlist wishlist);

    Wishlist toEntity(WishlistDto wishlistDto);

}
