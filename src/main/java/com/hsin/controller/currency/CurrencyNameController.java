package com.hsin.controller.currency;

import com.hsin.domain.currency.CurrencyName;
import com.hsin.service.currency.CurrencyNameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class CurrencyNameController {

    @Autowired
    private CurrencyNameService currencyNameService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("查詢全部")
    @GetMapping("/currency/name/all")
    public ResponseEntity<List<CurrencyName>> getAll() {
        try {
            List<CurrencyName> _currencyNames = new ArrayList<>(currencyNameService.findAll());

            if (_currencyNames.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_currencyNames, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("單筆查詢")
    @GetMapping("/currency/name/{currency_code}")
    public ResponseEntity<CurrencyName> getOne(@PathVariable("currency_code") String currencyCode) {
        try {
            Optional<CurrencyName> _currencyName = currencyNameService.findByCurrencyCode(currencyCode);
            if (_currencyName.isPresent()) {
                return new ResponseEntity<>(_currencyName.get(), HttpStatus.OK);
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
    @PostMapping("/currency/name/{currency_code}")
    public ResponseEntity<CurrencyName> insert(@PathVariable(name = "currency_code") String currencyCode,
                                               @RequestParam(name = "currency_name") String currencyName) {
        try {
            CurrencyName _currencyName = currencyNameService.save(
                    new CurrencyName(null, currencyCode, currencyName, new Date()));
            return new ResponseEntity<>(_currencyName, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("更新")
    @PutMapping("/currency/name/{currency_code}")
    public ResponseEntity<CurrencyName> update(@PathVariable(name = "currency_code") String currencyCode,
                                               @RequestParam(name = "currency_name") String currencyName) {
        try {
            Optional<CurrencyName> _currencyName = currencyNameService.findByCurrencyCode(currencyCode);
            if (_currencyName.isPresent()) {
                _currencyName.get().setCurrencyName(currencyName);
                _currencyName.get().setSubmissionDate(new Date());
                return new ResponseEntity<>(currencyNameService.save(_currencyName.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @ApiOperation("單筆刪除")
    @DeleteMapping("/currency/name/{currency_code}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "currency_code") String currencyCode) {
        try {
            int result = currencyNameService.deleteByCurrencyCode(currencyCode);
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
    @DeleteMapping("/currency/name/all")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            currencyNameService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
