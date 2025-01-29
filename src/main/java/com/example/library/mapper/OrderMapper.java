package com.example.library.mapper;

import com.example.library.dto.OrderDto;
import com.example.library.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderDto orderDto);

    void partialUpdate(OrderDto orderDto, @MappingTarget Order order);
}
