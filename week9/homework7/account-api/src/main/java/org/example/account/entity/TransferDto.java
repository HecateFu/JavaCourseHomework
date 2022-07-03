package org.example.account.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private String userCode;
    private String fromCurrencyType;
    private BigDecimal fromAmount;
    private String toCurrencyType;
    private BigDecimal toAmount;
}
