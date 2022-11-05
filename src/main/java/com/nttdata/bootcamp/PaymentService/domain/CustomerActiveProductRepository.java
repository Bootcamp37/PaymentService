package com.nttdata.bootcamp.PaymentService.domain;

import com.nttdata.bootcamp.PaymentService.domain.dbo.CustomerActiveProductRequest;
import com.nttdata.bootcamp.PaymentService.domain.dbo.CustomerActiveProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerActiveProductRepository {
    public static final String CUSTOMER_PRODUCT_SERVICE = "ms-customerProduct";
    @Value("${message.path-customerProductDomain}")
    public String urlCustomerProduct;
    @Value("${message.path-get}")
    public String pathGet;
    @Value("${message.path-update}")
    public String pathUpdate;
    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    public Mono<CustomerActiveProductResponse> getById(String idCustomerPassiveProduct) {
        log.info("====> CustomerActiveProductRepository: GetById");
        WebClient webClientProduct = WebClient.builder().baseUrl(urlCustomerProduct).build();
        return webClientProduct.get()
                .uri(pathGet + "{id}", idCustomerPassiveProduct)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Error 400")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Error 500")))
                .bodyToMono(CustomerActiveProductResponse.class)
                .transform(it -> reactiveCircuitBreakerFactory.create(CUSTOMER_PRODUCT_SERVICE)
                        .run(it, throwable -> Mono.just(new CustomerActiveProductResponse()))
                );
    }

    public Mono<CustomerActiveProductResponse> update(CustomerActiveProductRequest request, String id) {
        log.info("====> CustomerActiveProductRepository: Update");
        WebClient webClientProduct = WebClient.builder().baseUrl(urlCustomerProduct).build();
        return webClientProduct.put()
                .uri(pathUpdate + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Error 400")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Error 500")))
                .bodyToMono(CustomerActiveProductResponse.class)
                .transform(it -> reactiveCircuitBreakerFactory.create(CUSTOMER_PRODUCT_SERVICE)
                        .run(it, throwable -> Mono.just(new CustomerActiveProductResponse()))
                );
    }
}
