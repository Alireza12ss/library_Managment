package com.example.library.mapper;

import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.dto.CartItem.CartItemDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.entity.CartItem;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemDto toDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDto cartItemDto = new CartItemDto();

        cartItemDto.setBook( bookToResponseBookDto( cartItem.getBook() ) );
        cartItemDto.setQuantity( cartItem.getQuantity() );

        return cartItemDto;
    }

    @Override
    public CartItem toEntity(CartItemDto cartItemDto) {
        if ( cartItemDto == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setBook( responseBookDtoToBook( cartItemDto.getBook() ) );
        cartItem.setQuantity( cartItemDto.getQuantity() );

        return cartItem;
    }

    protected ResponseBookGroupDto bookGroupToResponseBookGroupDto(BookGroup bookGroup) {
        if ( bookGroup == null ) {
            return null;
        }

        ResponseBookGroupDto responseBookGroupDto = new ResponseBookGroupDto();

        responseBookGroupDto.setName( bookGroup.getName() );

        return responseBookGroupDto;
    }

    protected ResponseBookDto bookToResponseBookDto(Book book) {
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

    protected BookGroup responseBookGroupDtoToBookGroup(ResponseBookGroupDto responseBookGroupDto) {
        if ( responseBookGroupDto == null ) {
            return null;
        }

        BookGroup bookGroup = new BookGroup();

        bookGroup.setName( responseBookGroupDto.getName() );

        return bookGroup;
    }

    protected Book responseBookDtoToBook(ResponseBookDto responseBookDto) {
        if ( responseBookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setTitle( responseBookDto.getTitle() );
        book.setAuthor( responseBookDto.getAuthor() );
        book.setGroup( responseBookGroupDtoToBookGroup( responseBookDto.getGroup() ) );
        book.setPrice( responseBookDto.getPrice() );

        return book;
    }
}
