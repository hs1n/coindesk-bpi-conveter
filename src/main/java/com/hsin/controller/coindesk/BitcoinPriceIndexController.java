package com.hsin.controller.coindesk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsin.domain.coindesk.BitcoinPriceIndex;
import com.hsin.model.CurrentPrice;
import com.hsin.model.CurrentPriceConverted;
import com.hsin.service.coindesk.BitcoinPriceIndexService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class BitcoinPriceIndexController {
    @Autowired
    CurrentPrice currentPrice;
    @Autowired
    BitcoinPriceIndexService bitcoinPriceIndexService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("從 Coindesk API 取資料，只存 USD 的資料")
    @GetMapping("/coindesk/load")
    public ResponseEntity<BitcoinPriceIndex> loadUsdBpi() {
        BitcoinPriceIndex bitcoinPriceIndex = this.loadUSDBpiFromCoindesk();
        return new ResponseEntity<>(bitcoinPriceIndexService.save(bitcoinPriceIndex), HttpStatus.CREATED);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("綜合匯率結果查詢資料")
    @GetMapping("/coindesk/load/converted")
    public ResponseEntity<CurrentPriceConverted> loadConvertedData() {
        BitcoinPriceIndex bitcoinPriceIndex = this.loadUSDBpiFromCoindesk();
        CurrentPriceConverted currentPriceConverted = new CurrentPriceConverted();

        currentPriceConverted.setTime(bitcoinPriceIndex.getSubmissionDate());
        currentPriceConverted.setChartName("Bitcoin (converted)");
        currentPriceConverted.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD). Currency data converted using manually conversion rate.");
        currentPriceConverted.setBpi(bitcoinPriceIndexService.findByBpiId(bitcoinPriceIndexService.save(bitcoinPriceIndex).getId()));
        return new ResponseEntity<>(currentPriceConverted, HttpStatus.CREATED);
    }

    String loadCoindeskBpiJSON() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
    }

    BitcoinPriceIndex loadUSDBpiFromCoindesk() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略沒有在 Object 中設定的欄位
        try {
            currentPrice = mapper.readValue(this.loadCoindeskBpiJSON(), new TypeReference<CurrentPrice>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return currentPrice.getBpi().getUSD();
    }
}
