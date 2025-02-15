package com.example.library.mapper;

import com.example.library.dto.PaymentDto;
import com.example.library.entity.Payment;
import com.example.library.entity.Status;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:31:51+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDto toDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setAmount( payment.getAmount() );
        paymentDto.setPaymentDate( payment.getPaymentDate() );
        if ( payment.getStatus() != null ) {
            paymentDto.setStatus( payment.getStatus().name() );
        }

        return paymentDto;
    }

    @Override
    public Payment toEntity(PaymentDto paymentDto) {
        if ( paymentDto == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setAmount( paymentDto.getAmount() );
        payment.setPaymentDate( paymentDto.getPaymentDate() );
        if ( paymentDto.getStatus() != null ) {
            payment.setStatus( Enum.valueOf( Status.class, paymentDto.getStatus() ) );
        }

        return payment;
    }
}
