package com.example.library.mapper;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:31:49+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public ResponseBookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        ResponseBookDto responseBookDto = new ResponseBookDto();

        responseBookDto.setAuthor( book.getAuthor() );
        responseBookDto.setGroup( bookGroupToResponseBookGroupDto( book.getGroup() ) );
        responseBookDto.setPrice( book.getPrice() );
        responseBookDto.setTitle( book.getTitle() );

        return responseBookDto;
    }

    @Override
    public Book toEntity(CreateUpdateBookDto dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthor( dto.getAuthor() );
        book.setPrice( dto.getPrice() );
        book.setTitle( dto.getTitle() );

        return book;
    }

    @Override
    public Book partialUpdate(CreateUpdateBookDto dto, Book book) {
        if ( dto == null ) {
            return book;
        }

        book.setAuthor( dto.getAuthor() );
        book.setPrice( dto.getPrice() );
        book.setTitle( dto.getTitle() );

        return book;
    }

    protected ResponseBookGroupDto bookGroupToResponseBookGroupDto(BookGroup bookGroup) {
        if ( bookGroup == null ) {
            return null;
        }

        ResponseBookGroupDto responseBookGroupDto = new ResponseBookGroupDto();

        responseBookGroupDto.setName( bookGroup.getName() );

        return responseBookGroupDto;
    }
}
