package com.idfinance.cryptocurrencywatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idfinance.cryptocurrencywatcher.util.NoHtml;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptocurrencies", uniqueConstraints = {@UniqueConstraint(columnNames = {"id","symbol", "name"},
        name = "cryptocurrency_unique_id_symbol_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Cryptocurrency extends NamedEntity {

    @Column(name = "symbol", nullable = false)
    @NotBlank
    @Size(min = 2, max = 5)
    @NoHtml
    @JoinColumn(name = "symbol")
    private String symbol;

    @Column(name = "price_usd", nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal priceUsd;

    @Column(name = "date_time",
            columnDefinition = "default current_timestamp() on update current_timestamp()",
            nullable = false)
    private LocalDateTime dateTime;

    public Cryptocurrency(Integer id, String name, String symbol, BigDecimal priceUsd, LocalDateTime dateTime) {
        super(id, name);
        this.symbol = symbol;
        this.priceUsd = priceUsd;
        this.dateTime = dateTime;
    }
}
