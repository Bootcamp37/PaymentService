package com.nttdata.bootcamp.PaymentService.domain;

import com.nttdata.bootcamp.PaymentService.domain.dbo.CustomerActiveProductRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentMapper;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentRepository;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    @Autowired
    private final IPaymentRepository repository;
    @Autowired
    private final IPaymentMapper mapper;
    @Autowired
    private final CustomerActiveProductRepository customerProductRepository;
    @Override
    public Flux<PaymentResponse> getAll() {
        return repository.findAll()
                .map(mapper::toResponse);
    }

    @Override
    public Mono<PaymentResponse> getById(String id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<PaymentResponse> save(Mono<PaymentRequest> request) {
        // Existe la cuenta?
        return request.flatMap(e ->
            customerProductRepository.getById(e.getCustomerActiveProductId())
                .flatMap(customerActiveProductResponse -> {
                    // Coloca la fecha
                    e.setShoppingDate(getDate());
                    // Tiene saldo?
                    if ( (customerActiveProductResponse.getDebt() - e.getAmount()) < 0) {
                        // Retorna error
                        return Mono.error(RuntimeException::new);
                    }

                    CustomerActiveProductRequest update = new CustomerActiveProductRequest();
                    BeanUtils.copyProperties(customerActiveProductResponse, update);
                    update.setDebt(customerActiveProductResponse.getDebt() - e.getAmount());
                    // Actualizar saldo
                    return customerProductRepository.update(update, e.getCustomerActiveProductId())
                            // Guardar operacion
                            .flatMap(p -> Mono.just(e))
                            .map(mapper::toEntity)
                            .flatMap(repository::save)
                            .map(mapper::toResponse)
                            .switchIfEmpty(Mono.error(RuntimeException::new));
                })
                // No Existe
                // Mandar error
                .switchIfEmpty(Mono.error(RuntimeException::new)));
    }

    @Override
    public Mono<PaymentResponse> update(Mono<PaymentRequest> request, String id) {
        return Mono.just(new PaymentResponse());
    }

    @Override
    public Mono<PaymentResponse> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(deleteCustomer -> repository.delete(deleteCustomer)
                        .then(Mono.just(mapper.toResponse(deleteCustomer))));
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
