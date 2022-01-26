package com.idfinance.cryptocurrencywatcher.to;

import com.idfinance.cryptocurrencywatcher.util.NoHtml;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CryptocurrencyTo extends NamedTo {

    @NotBlank
    @Size(min = 2, max = 5)
    @NoHtml
    String symbol;

    public CryptocurrencyTo(Integer id, String name, String symbol) {
        super(id, name);
        this.symbol = symbol;
    }
}
