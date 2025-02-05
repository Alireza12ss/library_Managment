package com.example.library.mapper;

import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.dto.CartItem.CartItemDto;
import com.example.library.dto.ResponseCartDto;
import com.example.library.entity.Cart;
import com.example.library.entity.CartItem;
import com.example.library.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public ResponseCartDto toDto(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        ResponseCartDto responseCartDto = new ResponseCartDto();

        responseCartDto.setUser( userToResponseUserDto( cart.getUser() ) );
        responseCartDto.setCartItems( cartItemListToCartItemDtoList( cart.getCartItems() ) );

        return responseCartDto;
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

    protected List<CartItemDto> cartItemListToCartItemDtoList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemDto> list1 = new ArrayList<CartItemDto>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( cartItemMapper.toDto( cartItem ) );
        }

        return list1;
    }
}
