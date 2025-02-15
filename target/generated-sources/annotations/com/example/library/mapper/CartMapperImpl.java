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
    date = "2025-02-06T22:31:51+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
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

        responseCartDto.setCartItems( cartItemListToCartItemDtoList( cart.getCartItems() ) );
        responseCartDto.setUser( userToResponseUserDto( cart.getUser() ) );

        return responseCartDto;
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
}
