package com.example.library.mapper;

import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "group.name", target = "group")
    BookDto toDto(Book book);

    @Mapping(source = "group", target = "group.name")
    Book toEntity(BookDto bookDto);

    Book partialUpdate(BookDto bookDto, @MappingTarget Book book);
}
