package com.example.library.mapper;

import com.example.library.dto.BookRequest.CreateBookRequestDto;
import com.example.library.dto.BookRequest.ResponseBookRequestDto;
import com.example.library.entity.BookRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BookRequestMapperImpl implements BookRequestMapper {

    @Override
    public ResponseBookRequestDto toDto(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        ResponseBookRequestDto responseBookRequestDto = new ResponseBookRequestDto();

        responseBookRequestDto.setTitle( bookRequest.getTitle() );
        responseBookRequestDto.setAuthor( bookRequest.getAuthor() );

        return responseBookRequestDto;
    }

    @Override
    public BookRequest toEntity(CreateBookRequestDto createBookRequestDto) {
        if ( createBookRequestDto == null ) {
            return null;
        }

        BookRequest bookRequest = new BookRequest();

        bookRequest.setTitle( createBookRequestDto.getTitle() );
        bookRequest.setAuthor( createBookRequestDto.getAuthor() );

        return bookRequest;
    }
}
