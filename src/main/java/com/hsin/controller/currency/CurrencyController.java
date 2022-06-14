package com.hsin.controller.currency;

import com.hsin.domain.currency.Currency;
import com.hsin.service.currency.CurrencyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("查詢全部")
    @GetMapping("/currency/all")
    public ResponseEntity<List<Currency>> getAll() {
        try {

            List<Currency> _currencies = new ArrayList<>(currencyService.findAll());

            if (_currencies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_currencies, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("單筆查詢")
    @GetMapping("/currency")
    public ResponseEntity<Currency> getOne(@RequestParam("currency_code_from") String currencyCodeFrom,
                                           @RequestParam(name = "currency_code_to") String currencyCodeTo) {
        try {
            Optional<Currency> _currency = currencyService.findByCurrencyCodeFromAndCurrencyCodeTo(currencyCodeFrom, currencyCodeTo);
            if (_currency.isPresent()) {
                return new ResponseEntity<>(_currency.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("新增")
    @PostMapping("/currency")
    public ResponseEntity<Currency> insert(@RequestParam(name = "currency_code_from") String currencyCodeFrom,
                                           @RequestParam(name = "currency_code_to") String currencyCodeTo,
                                           @RequestParam(name = "conversion_rate") BigDecimal conversionRate) {
        try {
            Currency _currency = currencyService.save(
                    new Currency(null, currencyCodeFrom, currencyCodeTo, conversionRate, new Date()));
            return new ResponseEntity<>(_currency, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("更新")
    @PutMapping("/currency")
    public ResponseEntity<Currency> update(@RequestParam(name = "currency_code_from") String currencyCodeFrom,
                                           @RequestParam(name = "currency_code_to") String currencyCodeTo,
                                           @RequestParam(name = "conversion_rate") BigDecimal conversionRate) {
        try {
            Optional<Currency> _currency = currencyService.findByCurrencyCodeFromAndCurrencyCodeTo(currencyCodeFrom, currencyCodeTo);
            if (_currency.isPresent()) {
                _currency.get().setConversionRate(conversionRate);
                _currency.get().setSubmissionDate(new Date());
                return new ResponseEntity<>(currencyService.save(_currency.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("單筆刪除")
    @DeleteMapping("/currency")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "currency_code_from") String currencyCodeFrom,
                                             @RequestParam(name = "currency_code_to") String currencyCodeTo) {
        try {
            int result = currencyService.deleteByCurrencyCodeFromAndCurrencyCodeTo(currencyCodeFrom, currencyCodeTo);
            if (result == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("全部刪除")
    @DeleteMapping("/currency/all")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            currencyService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
