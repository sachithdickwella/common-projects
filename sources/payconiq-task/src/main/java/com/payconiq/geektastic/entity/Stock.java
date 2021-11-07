package com.payconiq.geektastic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public record Stock(@Nullable UUID id,
                    @Nullable String name,
                    @Nullable String currency,
                    @Nullable Double price,
                    @Nullable Integer quantity,
                    @Nullable LocalDateTime createDateTime,
                    @Nullable LocalDateTime lastUpdatedDateTime) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Stock.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("currency='" + currency + "'")
                .add("price=" + price)
                .add("quantity=" + quantity)
                .add("createDateTime=" + createDateTime)
                .add("lastUpdatedDateTime=" + lastUpdatedDateTime)
                .toString();
    }

    /**
     * Create a new instance of {@link Stock} record enriched with give parameter
     * values hence it's immutability.
     *
     * @param id                  instance of {@link UUID} to denote stock id.
     * @param createDateTime      instance of {@link LocalDateTime} to specify stock
     *                            created date and time.
     * @param lastUpdatedDateTime instance of {@link LocalDateTime} to specify stock
     *                            last updated date time.
     * @return a new instance of {@link Stock} with the details of this object.
     */
    @NotNull
    public Stock enrich(@NotNull UUID id,
                        @NotNull LocalDateTime createDateTime,
                        @NotNull LocalDateTime lastUpdatedDateTime) {
        return new Stock(id, name, currency, price, quantity, createDateTime, lastUpdatedDateTime);
    }

    /**
     * Covert the class into a comma-delimited representation before save into
     * persistence store.
     *
     * @return a CSV line representation of the class attributes.
     */
    @NotNull
    public String toCSV() {
        DateTimeFormatter formatter = CONST_DEFAULT_DATETIME_FORMAT.instance(DateTimeFormatter.class);
        return new StringJoiner(",")
                .add(id != null ? id.toString() : "null")
                .add(name)
                .add(currency)
                .add(valueOf(price))
                .add(valueOf(quantity))
                .add(createDateTime != null ? createDateTime.format(formatter) : "null")
                .add(lastUpdatedDateTime != null ? lastUpdatedDateTime.format(formatter) : "null")
                .toString();
    }
}
