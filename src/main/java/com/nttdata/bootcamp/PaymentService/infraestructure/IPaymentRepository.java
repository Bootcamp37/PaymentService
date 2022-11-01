package com.nttdata.bootcamp.PaymentService.infraestructure;

import com.nttdata.bootcamp.PaymentService.domain.entity.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends ReactiveMongoRepository<Payment, String> {
}
