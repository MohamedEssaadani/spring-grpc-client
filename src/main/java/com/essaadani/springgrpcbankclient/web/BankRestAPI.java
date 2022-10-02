package com.essaadani.springgrpcbankclient.web;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.essaadani.springgrpcservice.grpc.stub.Bank;
import org.essaadani.springgrpcservice.grpc.stub.BankServiceGrpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestAPI {

    @GrpcClient("bank")
    BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    @GetMapping("/convert")
    public ConvertCurrencyResponseDto convertCurrency(@RequestParam  String from, @RequestParam  String to, @RequestParam  double amount){
        Bank.ConvertCurrencyRequest request =  Bank.ConvertCurrencyRequest.newBuilder()
                .setAmount(amount)
                .setCurrencyFrom(from)
                .setCurrencyTo(to)
                .build();

        Bank.ConvertCurrencyResponse response =  bankServiceBlockingStub.convertCurrency(request);

        return new ConvertCurrencyResponseDto(response.getCurrencyFrom(), response.getCurrencyTo(), response.getAmount(), response.getConversionResult());
    }

}


record ConvertCurrencyResponseDto(String from, String to, double amount, double result){}
