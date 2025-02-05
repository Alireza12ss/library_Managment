package com.example.library.mapper;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    //@Mapping(source = "group.name", target = "group")
    ResponseBookDto toDto(Book book);
    @Mapping(target = "group" , ignore = true)
    Book toEntity(CreateUpdateBookDto dto);
    @Mapping(target = "group" , ignore = true)
    Book partialUpdate(CreateUpdateBookDto dto, @MappingTarget Book book);
}
