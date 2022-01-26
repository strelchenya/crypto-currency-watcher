package com.idfinance.cryptocurrencywatcher.repository;

import com.idfinance.cryptocurrencywatcher.model.Cryptocurrency;
import com.idfinance.cryptocurrencywatcher.to.CryptocurrencyTo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CryptocurrencyRepository extends BaseRepository<Cryptocurrency> {

    Optional<Cryptocurrency> findBySymbol(String symbol);

    @Query("""
            SELECT new com.idfinance.cryptocurrencywatcher.to.CryptocurrencyTo(c.id, c.name, c.symbol)\040
            FROM Cryptocurrency c\040
            ORDER BY c.name
            """)
    List<CryptocurrencyTo> getAll();
}
