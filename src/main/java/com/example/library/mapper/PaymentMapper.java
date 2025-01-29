package com.example.library.mapper;

import com.example.library.dto.PaymentDto;
import com.example.library.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "order.id", target = "orderId")
    PaymentDto toDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "orderId", target = "order.id")
    Payment toEntity(PaymentDto paymentDto);

    void partialUpdate(PaymentDto paymentDto, @MappingTarget Payment payment);
}
