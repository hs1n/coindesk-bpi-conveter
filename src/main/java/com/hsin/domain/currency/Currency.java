package com.hsin.domain.currency;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_currency")
@ApiModel(description = "Currency exchange rate")
@IdClass(CurrencyId.class)
public class Currency {
    @Column(name = "id")
    private Long id;

    @Id
    @Column(name = "currency_code_from", length = 3, nullable = false)
    @JsonProperty("currency_code_from")
    @ApiModelProperty(value = "Currency code (from)", required = true)
    private String currencyCodeFrom;

    @Id
    @Column(name = "currency_code_to", length = 3, nullable = false)
    @JsonProperty("currency_code_to")
    @ApiModelProperty(value = "Currency code (to)", required = true)
    private String currencyCodeTo;

    @Column(name = "conversion_rate", precision = 19, scale = 4, nullable = false)
    @JsonProperty("conversion_rate")
    @ApiModelProperty(value = "Conversion Rate", required = true)
    private BigDecimal conversionRate;

    @Column(name = "submission_date", nullable = false)
    @JsonProperty("submission_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty(value = "Submission Date", required = true)
    private Date submissionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Currency currency = (Currency) o;
        return id != null && Objects.equals(id, currency.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}