package com.nttdata.bootcamp.PaymentService.infraestructure;

import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPaymentService {
    Flux<PaymentResponse> getAll();

    Mono<PaymentResponse> getById(String id);

    Mono<PaymentResponse> save(PaymentRequest request);

    Mono<PaymentResponse> update(PaymentRequest request, String id);

    Mono<PaymentResponse> delete(String id);
}
