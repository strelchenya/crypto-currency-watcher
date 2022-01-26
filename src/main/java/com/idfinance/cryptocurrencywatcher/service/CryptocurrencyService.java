package com.idfinance.cryptocurrencywatcher.service;

import com.idfinance.cryptocurrencywatcher.error.NotFoundException;
import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import com.idfinance.cryptocurrencywatcher.to.CryptocurrencyTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CryptocurrencyService {

    private final CryptocurrencyRepository repository;

    public Cryptocurrency findBySymbol(String symbol){
        return repository.findBySymbol(symbol.toUpperCase()).orElseThrow(() ->
                new NotFoundException("Not found cryptocurrency by symbol: " + symbol));
    }

    public List<CryptocurrencyTo> getAll(){
        return repository.getAll();
    }
}
