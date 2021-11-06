package com.payconiq.geektastic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import static com.payconiq.geektastic.util.AppConstants.CONST_DEFAULT_DATETIME_FORMAT;
import static java.lang.String.valueOf;

/**
 * Data transfer object to orchestrate {@link com.payconiq.geektastic.controller.StockController}
 * services.
 *
 * @param id                  instance of {@link UUID} to denote stock id.
 * @param name                instance of {@link String} to denote stock name.
 * @param currency            instance of {@link String} to denote currency of the price.
 * @param price               instance of {@link Double} to denote stock's unit
 *                            price.
 * @param quantity            instance of {@link Integer} to denote stock's item
 *                            quantity.
 * @param createDateTime      instance of {@link LocalDateTime} to specify stock
 *                            created date and time.
 * @param lastUpdatedDateTime instance of {@link LocalDateTime} to specify stock
 *                            last updated date time.
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Stock(@NotNull UUID id,
                    @NotNull String name,
                    @NotNull String currency,
                    @NotNull Double price,
                    @NotNull Integer quantity,
                    @NotNull LocalDateTime createDateTime,
                    @NotNull LocalDateTime lastUpdatedDateTime) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id.equals(stock.id) && name.equals(stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = CONST_DEFAULT_DATETIME_FORMAT.instance(DateTimeFormatter.class);
        return new StringJoiner(",")
                .add(id.toString())
                .add(name)
                .add(currency)
                .add(valueOf(price))
                .add(valueOf(quantity))
                .add(createDateTime.format(formatter))
                .add(lastUpdatedDateTime.format(formatter))
                .toString();
    }
}
