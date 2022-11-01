package com.nttdata.bootcamp.PaymentService.domain.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActiveProductRequest {
    private String customerId;
    private String productId;
    private Double lineOfCredit;
    private Double debt;
}
