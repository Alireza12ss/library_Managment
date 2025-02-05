package com.example.library.mapper;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(source = "group.name", target = "group")
    ResponseBookDto toDto(Book book);
    @Mapping(source = "group", target = "group.name")
    Book toEntity(CreateUpdateBookDto dto);
    @Mapping(source = "group", target = "group.name")
    Book partialUpdate(CreateUpdateBookDto dto, @MappingTarget Book book);
}
