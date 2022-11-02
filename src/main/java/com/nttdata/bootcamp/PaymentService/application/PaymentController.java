package com.nttdata.bootcamp.PaymentService.application;

import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.PaymentResponse;
import com.nttdata.bootcamp.PaymentService.infraestructure.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("${message.path-payment}")
@RefreshScope
public class PaymentController {
    @Autowired
    private final IPaymentService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Flux<PaymentResponse> getAll() {
        return service.getAll();
    }
    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Mono<PaymentResponse>> getById(@PathVariable String id) {
        Mono<PaymentResponse> paymentResponseMono = service.getById(id);
        return new ResponseEntity<>(paymentResponseMono, paymentResponseMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PaymentResponse> save(@RequestBody PaymentRequest request) {
        return service.save(Mono.just(request));
    }

    @PutMapping("/update/{id}")
    public Mono<PaymentResponse> update(@RequestBody PaymentRequest request, @PathVariable String id) {
        return service.update(Mono.just(request), id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.delete(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
