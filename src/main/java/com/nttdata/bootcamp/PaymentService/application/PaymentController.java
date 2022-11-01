package com.nttdata.bootcamp.PaymentService.application;

import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-payment}")
public class PaymentController {
    @Autowired
    private final IPaymentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<PaymentResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<PaymentResponse> getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Mono<PaymentResponse> save(@RequestBody PaymentRequest request) {
        return service.save(request);
    }

    @PutMapping("/update/{id}")
    public Mono<PaymentResponse> update(@RequestBody PaymentRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<PaymentResponse> delete(@PathVariable String id) {
        return service.delete(id);
    }
}
