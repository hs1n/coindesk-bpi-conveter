package com.hsin.repository.currency;

import com.hsin.domain.currency.CurrencyName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyNameRepository extends JpaRepository<CurrencyName, Long> {
    Optional<CurrencyName> findByCurrencyCode(String currencyCode);

    int deleteByCurrencyCode(String currencyCode);
}