package org.malik.micro.currency_conversion_service.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchange implements Serializable {
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;
    private String environment;
}
