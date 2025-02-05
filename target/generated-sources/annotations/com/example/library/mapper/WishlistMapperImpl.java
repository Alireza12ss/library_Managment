package com.example.library.mapper;

import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.BookGroup.ResponseBookGroupDto;
import com.example.library.dto.WishlistDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookGroup;
import com.example.library.entity.User;
import com.example.library.entity.Wishlist;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class WishlistMapperImpl implements WishlistMapper {

    @Override
    public WishlistDto toDto(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }

        WishlistDto wishlistDto = new WishlistDto();

        wishlistDto.setUser( userToResponseUserDto( wishlist.getUser() ) );
        wishlistDto.setBook( bookToResponseBookDto( wishlist.getBook() ) );

        return wishlistDto;
    }

    @Override
    public Wishlist toEntity(WishlistDto wishlistDto) {
        if ( wishlistDto == null ) {
            return null;
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setUser( responseUserDtoToUser( wishlistDto.getUser() ) );
        wishlist.setBook( responseBookDtoToBook( wishlistDto.getBook() ) );

        return wishlist;
    }

    protected ResponseUserDto userToResponseUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDto responseUserDto = new ResponseUserDto();

        responseUserDto.setId( user.getId() );
        responseUserDto.setUsername( user.getUsername() );
        responseUserDto.setPassword( user.getPassword() );
        responseUserDto.setRole( user.getRole() );

        return responseUserDto;
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

    protected User responseUserDtoToUser(ResponseUserDto responseUserDto) {
        if ( responseUserDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( responseUserDto.getId() );
        user.setUsername( responseUserDto.getUsername() );
        user.setPassword( responseUserDto.getPassword() );
        user.setRole( responseUserDto.getRole() );

        return user;
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
