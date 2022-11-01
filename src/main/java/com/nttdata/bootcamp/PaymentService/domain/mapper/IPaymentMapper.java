package com.nttdata.bootcamp.PaymentService.domain.mapper;

import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import com.nttdata.bootcamp.PaymentService.domain.entity.Payment;

public interface IPaymentMapper {
    Payment toEntity(PaymentRequest request);

    PaymentResponse toResponse(Payment payment);
}
