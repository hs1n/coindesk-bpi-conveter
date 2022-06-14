package com.hsin.repository.coindesk;

import com.hsin.domain.coindesk.BitcoinPriceIndexView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitcoinPriceIndexViewRepository extends JpaRepository<BitcoinPriceIndexView, Long> {
    List<BitcoinPriceIndexView> findByBpiId(Long bpiId);
}