package com.example.library.mapper;

import com.example.library.dto.BookGroup.CreateUpdateBookGroupDto;
import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.entity.BookGroup;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BookGroupMapperImpl implements BookGroupMapper {

    @Override
    public ResponseBookGroupDto toDto(BookGroup bookGroup) {
        if ( bookGroup == null ) {
            return null;
        }

        ResponseBookGroupDto responseBookGroupDto = new ResponseBookGroupDto();

        responseBookGroupDto.setName( bookGroup.getName() );

        return responseBookGroupDto;
    }

    @Override
    public BookGroup toEntity(CreateUpdateBookGroupDto bookGroupReqDto) {
        if ( bookGroupReqDto == null ) {
            return null;
        }

        BookGroup bookGroup = new BookGroup();

        bookGroup.setName( bookGroupReqDto.getName() );

        return bookGroup;
    }

    @Override
    public BookGroup partialUpdate(CreateUpdateBookGroupDto bookGroupReqDto, BookGroup bookGroup) {
        if ( bookGroupReqDto == null ) {
            return bookGroup;
        }

        bookGroup.setName( bookGroupReqDto.getName() );

        return bookGroup;
    }
}
