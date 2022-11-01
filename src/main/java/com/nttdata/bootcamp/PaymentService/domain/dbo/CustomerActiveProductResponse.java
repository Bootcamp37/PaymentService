package com.nttdata.bootcamp.PaymentService.domain.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActiveProductResponse {
    private String id;
    private String customerId;
    private String customerUrl;
    private String productUrl;
    private String productId;
    private Double lineOfCredit;
    private Double debt;
}
