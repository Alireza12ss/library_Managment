package com.example.library.mapper;

import com.example.library.dto.ResponseOrderDto;
import com.example.library.entity.Order;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
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
        responseOrderDto.setUser( userMapper.toDto( order.getUser() ) );
        responseOrderDto.setOrderDate( order.getOrderDate() );
        responseOrderDto.setTotalPrice( order.getTotalPrice() );
        responseOrderDto.setPaymentCompleted( order.isPaymentCompleted() );

        return responseOrderDto;
    }
}
