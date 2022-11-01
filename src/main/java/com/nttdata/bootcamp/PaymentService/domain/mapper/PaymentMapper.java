package com.nttdata.bootcamp.PaymentService.domain.mapper;

import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import com.nttdata.bootcamp.PaymentService.domain.entity.Payment;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper implements IPaymentMapper {
    @Override
    public Payment toEntity(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setCustomerActiveProductId(request.getCustomerActiveProductId());
        payment.setAmount(request.getAmount());
        payment.setShoppingDate(request.getShoppingDate());
        return payment;
    }

    @Override
    public PaymentResponse toResponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(payment.getId());
        paymentResponse.setCustomerActiveProductId(payment.getCustomerActiveProductId());
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setShoppingDate(payment.getShoppingDate());
        return paymentResponse;
    }
}
