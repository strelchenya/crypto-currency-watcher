package com.idfinance.cryptocurrencywatcher.repository;

import com.idfinance.cryptocurrencywatcher.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);

    List<User> findAllBySymbolOrderByName(String symbol);
}
