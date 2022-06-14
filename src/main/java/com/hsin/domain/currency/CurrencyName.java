package com.hsin.domain.currency;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_currency_name")
@ApiModel(description = "Currency name")
public class CurrencyName {
    @Column(name = "id", nullable = false, unique = true)
    @JsonIgnore
    private Long id;

    @Id
    @Column(name = "currency_code", length = 3, nullable = false, unique = true)
    @JsonProperty("currency_code")
    @ApiModelProperty(value = "Currency code", required = true)
    private String currencyCode;

    @Column(name = "currency_name", length = 50, nullable = false)
    @JsonProperty("currency_name")
    @ApiModelProperty(value = "Currency name (cht)", required = true)
    private String currencyName;

    @Column(name = "submission_date", nullable = false)
    @JsonProperty("submission_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @ApiModelProperty(value = "Submission Date", required = true)
    private Date submissionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CurrencyName that = (CurrencyName) o;
        return currencyCode != null && Objects.equals(currencyCode, that.currencyCode);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
