package com.idfinance.cryptocurrencywatcher.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {
    private int id;
    private String symbol;
    private String name;
    private BigDecimal price_usd;
}
