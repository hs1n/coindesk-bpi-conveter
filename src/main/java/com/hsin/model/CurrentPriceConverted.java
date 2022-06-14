package com.hsin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hsin.domain.coindesk.BitcoinPriceIndexView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hsu
 * @since 2022/06/11
 */
@Data
@ApiModel(description = "Coindesk BPI data converted")
@Component
public class CurrentPriceConverted {

    @JsonProperty("time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Taipei")
    @ApiModelProperty(value = "Update date in yyyy/MM/dd HH:mm:ss format", required = true)
    private Date time;

    @JsonProperty("disclaimer")
    @ApiModelProperty(value = "Disclaimer", required = true)
    private String disclaimer;

    @JsonProperty("chartName")
    @ApiModelProperty(value = "Chart Name", required = true)
    private String chartName;

    @JsonProperty("bpi")
    @ApiModelProperty(value = "Bitcoin Price Indexes", required = true)
    @ElementCollection
    private List<BitcoinPriceIndexView> bpi = new ArrayList<>();
}