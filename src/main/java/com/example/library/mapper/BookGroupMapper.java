package com.example.library.mapper;

import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.dto.BookGroup.CreateUpdateBookGroupDto;
import com.example.library.entity.BookGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookGroupMapper {
    ResponseBookGroupDto  toDto(BookGroup bookGroup);

    BookGroup toEntity(CreateUpdateBookGroupDto bookGroupReqDto);

    BookGroup partialUpdate(CreateUpdateBookGroupDto bookGroupReqDto, @MappingTarget BookGroup bookGroup);
}
