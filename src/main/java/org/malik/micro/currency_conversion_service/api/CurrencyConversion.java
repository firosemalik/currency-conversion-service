package org.malik.micro.currency_conversion_service.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Getter
@Builder
@AllArgsConstructor
public class CurrencyConversion implements Serializable {

    private String from;
    private String to;
    private BigDecimal conversion;
    private Integer quantity;
    private BigDecimal finalAmount;
    private String environment;

}
