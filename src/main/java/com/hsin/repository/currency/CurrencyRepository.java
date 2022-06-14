package com.hsin.repository.currency;

import com.hsin.domain.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCurrencyCodeFromAndCurrencyCodeTo(String currencyCodeFrom, String currencyCodeTo);

    int deleteByCurrencyCodeFromAndCurrencyCodeTo(String currencyCodeFrom, String currencyCodeTo);
}