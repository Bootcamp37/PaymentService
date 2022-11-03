package com.nttdata.bootcamp.PaymentService.domain.mapper;

import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import com.nttdata.bootcamp.PaymentService.domain.entity.Payment;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentMapper implements IPaymentMapper {
    @Override
    public Payment toEntity(PaymentRequest request) {
        log.debug("====> PaymentMapper: ToEntity");
        Payment payment = new Payment();
        BeanUtils.copyProperties(request, payment);
        return payment;
    }

    @Override
    public PaymentResponse toResponse(Payment payment) {
        log.debug("====> PaymentMapper: ToResponse");
        PaymentResponse paymentResponse = new PaymentResponse();
        BeanUtils.copyProperties(payment, paymentResponse);
        return paymentResponse;
    }
}
