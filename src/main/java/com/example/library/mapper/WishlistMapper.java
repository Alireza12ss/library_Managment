package com.example.library.mapper;

import com.example.library.dto.WishlistDto;
import com.example.library.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "book.id", target = "bookId")
    WishlistDto toDto(Wishlist wishlist);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "bookId", target = "book.id")
    Wishlist toEntity(WishlistDto wishlistDto);

    void partialUpdate(WishlistDto wishlistDto, @MappingTarget Wishlist wishlist);
}
