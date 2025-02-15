package com.example.library.mapper;

import com.example.library.dto.BookRequest.CreateBookRequestDto;
import com.example.library.dto.BookRequest.ResponseBookRequestDto;
import com.example.library.entity.BookRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:31:51+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class BookRequestMapperImpl implements BookRequestMapper {

    @Override
    public ResponseBookRequestDto toDto(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        ResponseBookRequestDto responseBookRequestDto = new ResponseBookRequestDto();

        responseBookRequestDto.setAuthor( bookRequest.getAuthor() );
        responseBookRequestDto.setTitle( bookRequest.getTitle() );

        return responseBookRequestDto;
    }

    @Override
    public BookRequest toEntity(CreateBookRequestDto createBookRequestDto) {
        if ( createBookRequestDto == null ) {
            return null;
        }

        BookRequest bookRequest = new BookRequest();

        bookRequest.setAuthor( createBookRequestDto.getAuthor() );
        bookRequest.setTitle( createBookRequestDto.getTitle() );

        return bookRequest;
    }
}
