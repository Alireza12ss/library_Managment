package com.example.library.mapper;

import com.example.library.dto.BookGroupDto;
import com.example.library.dto.BookGroupReqDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookGroupMapper {

    BookGroupDto toDto(BookGroup bookGroup);

    BookGroup toEntity(BookGroupReqDto bookGroupReqDto);

    BookGroup partialUpdate(BookGroupReqDto bookGroupReqDto, @MappingTarget BookGroup bookGroup);
}
