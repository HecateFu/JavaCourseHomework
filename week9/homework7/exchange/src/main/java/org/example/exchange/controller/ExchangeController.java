package org.example.exchange.controller;

import org.example.exchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping("exchange")
    public String exchange(){
        return exchangeService.exchange();
    }
}
