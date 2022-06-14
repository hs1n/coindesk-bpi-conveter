package com.hsin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hsin.domain.coindesk.BitcoinPriceIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * CoinDesk is an information center on Bitcoins, a decentralized digital currency used around the world.
 * One service that CoinDesk provides is the Bitcoin Price Index (BPI). This Bitcoin pricing data is calculated
 * every minute and is published in USD, EUR, and GBP. BPI data is made available programmatically via REST API.
 * People can use the API however they like as long as they credit CoinDesk as the data source.
 * <p>
 * <a href="https://api.coindesk.com/v1/bpi/currentprice.json"></a>
 *
 * @author Hsu
 * @since 2022/06/11
 */
@Data
@ApiModel(description = "Coindesk BPI data")
@Component
public class CurrentPrice {

    @JsonProperty("time")
    @ApiModelProperty(value = "Time object", required = true)
    private Time time;

    @JsonProperty("disclaimer")
    @ApiModelProperty(value = "Disclaimer", required = true)
    private String disclaimer;

    @JsonProperty("chartName")
    @ApiModelProperty(value = "Chart Name", required = true)
    private String chartName;

    @JsonProperty("bpi")
    @ApiModelProperty(value = "Bitcoin Price Indexes", required = true)
    private BPI bpi;

    @Data
    public static class Time {
        @JsonProperty("updated")
        @ApiModelProperty(value = "Updated time", required = true)
        private String updated;

        @JsonProperty("updatedISO")
        @ApiModelProperty(value = "Updated time (ISO 8601)", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
        private Date updatedISO;

        @JsonProperty("updateduk")
        @ApiModelProperty(value = "Updated time (duk)", required = true)
        private String updateduk;
    }

    @Data
    public static class BPI {
        @JsonProperty("USD")
        @ApiModelProperty(value = "USD BPI", required = true)
        private BitcoinPriceIndex USD;

        @JsonProperty("GBP")
        @ApiModelProperty(value = "GBP BPI", required = true)
        private BitcoinPriceIndex GBP;

        @JsonProperty("EUR")
        @ApiModelProperty(value = "EUR BPI", required = true)
        private BitcoinPriceIndex EUR;
    }
}