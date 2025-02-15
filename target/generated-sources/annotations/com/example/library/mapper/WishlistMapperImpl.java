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
    date = "2025-02-06T22:31:45+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class WishlistMapperImpl implements WishlistMapper {

    @Override
    public WishlistDto toDto(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }

        WishlistDto wishlistDto = new WishlistDto();

        wishlistDto.setBook( bookToResponseBookDto( wishlist.getBook() ) );
        wishlistDto.setUser( userToResponseUserDto( wishlist.getUser() ) );

        return wishlistDto;
    }

    @Override
    public Wishlist toEntity(WishlistDto wishlistDto) {
        if ( wishlistDto == null ) {
            return null;
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setBook( responseBookDtoToBook( wishlistDto.getBook() ) );
        wishlist.setUser( responseUserDtoToUser( wishlistDto.getUser() ) );

        return wishlist;
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

        responseBookDto.setAuthor( book.getAuthor() );
        responseBookDto.setGroup( bookGroupToResponseBookGroupDto( book.getGroup() ) );
        responseBookDto.setPrice( book.getPrice() );
        responseBookDto.setTitle( book.getTitle() );

        return responseBookDto;
    }

    protected ResponseUserDto userToResponseUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDto responseUserDto = new ResponseUserDto();

        responseUserDto.setId( user.getId() );
        responseUserDto.setPassword( user.getPassword() );
        responseUserDto.setRole( user.getRole() );
        responseUserDto.setUsername( user.getUsername() );

        return responseUserDto;
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

        book.setAuthor( responseBookDto.getAuthor() );
        book.setGroup( responseBookGroupDtoToBookGroup( responseBookDto.getGroup() ) );
        book.setPrice( responseBookDto.getPrice() );
        book.setTitle( responseBookDto.getTitle() );

        return book;
    }

    protected User responseUserDtoToUser(ResponseUserDto responseUserDto) {
        if ( responseUserDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( responseUserDto.getId() );
        user.setPassword( responseUserDto.getPassword() );
        user.setRole( responseUserDto.getRole() );
        user.setUsername( responseUserDto.getUsername() );

        return user;
    }
}
