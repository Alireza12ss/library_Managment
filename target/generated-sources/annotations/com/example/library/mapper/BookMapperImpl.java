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
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public ResponseBookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        ResponseBookDto responseBookDto = new ResponseBookDto();

        responseBookDto.setTitle( book.getTitle() );
        responseBookDto.setAuthor( book.getAuthor() );
        responseBookDto.setGroup( bookGroupToResponseBookGroupDto( book.getGroup() ) );
        responseBookDto.setPrice( book.getPrice() );

        return responseBookDto;
    }

    @Override
    public Book toEntity(CreateUpdateBookDto dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setTitle( dto.getTitle() );
        book.setAuthor( dto.getAuthor() );
        book.setPrice( dto.getPrice() );

        return book;
    }

    @Override
    public Book partialUpdate(CreateUpdateBookDto dto, Book book) {
        if ( dto == null ) {
            return book;
        }

        book.setTitle( dto.getTitle() );
        book.setAuthor( dto.getAuthor() );
        book.setPrice( dto.getPrice() );

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
