package com.hsin.repository.coindesk;

import com.hsin.domain.coindesk.BitcoinPriceIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinPriceIndexRepository extends JpaRepository<BitcoinPriceIndex, Long> {
}