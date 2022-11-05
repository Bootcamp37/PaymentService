package com.nttdata.bootcamp.PaymentService.domain.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String customerActiveProductId;
    private Double amount;
    private String date;
}
