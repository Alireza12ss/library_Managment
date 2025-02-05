package com.example.library.mapper;

import com.example.library.dto.ResponseOrderDto;
import com.example.library.entity.Order;
import com.example.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface OrderMapper {
    ResponseOrderDto toDto(Order order);

}
