package com.example.library.mapper;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookGroupDto;
import com.example.library.dto.BookRequestDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.entity.BookRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    private static ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        BookMapper.modelMapper = modelMapper;
    }

    public static BookDto mapToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());

        if (book.getGroup() != null) {
            bookDto.setGroup(book.getGroup().getName());
        } else {
            bookDto.setGroup(null);
        }

        return bookDto;
    }

    public static Book mapToBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        if (book.getGroup() != null) {
            bookDto.setGroup(book.getGroup().getName());
        } else {
            bookDto.setGroup(null); // No group assigned
        }
        return book;
    }


    public static BookGroupDto mapToBookGroupDto(BookGroup bookGroup) {
        BookGroupDto bookGroupDto = modelMapper.map(bookGroup, BookGroupDto.class);
        if (bookGroup.getBooks() != null) {
            bookGroupDto.setBooks(
                    bookGroup.getBooks().stream()
                            .map(BookMapper::mapToBookDto) // Use existing Book mapping
                            .collect(Collectors.toList())
            );
        }
        return bookGroupDto;
    }

    public static BookRequestDto mapToBookRequestDto(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, BookRequestDto.class);
    }


}
