package com.example.library.mapper;

import com.example.library.dto.ResponseOrderDto;
import com.example.library.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cart.cartItems", target = "cartItems")
    ResponseOrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    Order toEntity(ResponseOrderDto orderDto);

    void partialUpdate(ResponseOrderDto orderDto, @MappingTarget Order order);
}
