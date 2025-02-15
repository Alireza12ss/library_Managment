package com.example.library.mapper;

import com.example.library.dto.ResponseOrderDto;
import com.example.library.entity.Order;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:31:51+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseOrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        ResponseOrderDto responseOrderDto = new ResponseOrderDto();

        responseOrderDto.setId( order.getId() );
        responseOrderDto.setOrderDate( order.getOrderDate() );
        responseOrderDto.setPaymentCompleted( order.isPaymentCompleted() );
        responseOrderDto.setTotalPrice( order.getTotalPrice() );
        responseOrderDto.setUser( userMapper.toDto( order.getUser() ) );

        return responseOrderDto;
    }
}
