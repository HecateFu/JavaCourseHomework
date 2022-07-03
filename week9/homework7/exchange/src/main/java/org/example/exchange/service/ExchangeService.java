package org.example.exchange.service;

import org.dromara.hmily.annotation.HmilyTCC;
import org.example.account.entity.TransferDto;
import org.example.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeService {
    @Autowired
    private AccountService accountService;

    @HmilyTCC
    public String exchange(){
        TransferDto transferA = new TransferDto();
        transferA.setUserCode("a");
        transferA.setFromAmount(new BigDecimal("1"));
        transferA.setFromCurrencyType("usd");
        transferA.setToAmount(new BigDecimal("7"));
        transferA.setToCurrencyType("cny");
        accountService.exchange(transferA);

        TransferDto transferB = new TransferDto();
        transferB.setUserCode("b");
        transferB.setFromAmount(new BigDecimal("7"));
        transferB.setFromCurrencyType("cny");
        transferB.setToAmount(new BigDecimal("1"));
        transferB.setToCurrencyType("usd");
        accountService.exchange(transferB);
        return "success";
    }
}
