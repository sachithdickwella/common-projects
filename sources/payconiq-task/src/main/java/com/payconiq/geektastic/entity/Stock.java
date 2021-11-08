package com.payconiq.geektastic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
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
 * @version 1.0.0
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "currency", "price", "quantity", "createDateTime", "lastUpdatedDateTime"})
public class Stock extends Entity {

    /**
     * {@link String} to denote stock name
     */
    private String name;
    /**
     * {@link String} to denote currency of the price
     */
    private String currency;
    /**
     * instance of {@link Double} to denote stock's unit
     * price.
     */
    private Double price;
    /**
     * instance of {@link Integer} to denote stock's item
     * quantity.
     */
    private Integer quantity;

    /**
     * All-args constructor to implement immutability using builder pattern with lombok.
     *
     * @param id                  instance of {@link UUID} to denote stock id.
     * @param name                instance of {@link String} to denote stock name.
     * @param currency            instance of {@link String} to denote currency of
     *                            the price.
     * @param price               instance of {@link Double} to denote stock's unit
     *                            price.
     * @param quantity            instance of {@link Integer} to denote stock's item
     *                            quantity.
     * @param createDateTime      instance of {@link LocalDateTime} to specify stock
     *                            created date and time.
     * @param lastUpdatedDateTime instance of {@link LocalDateTime} to specify stock
     *                            last updated date time.
     */
    public Stock(@NotNull UUID id,
                 @NotNull String name,
                 @NotNull String currency,
                 @NotNull Double price,
                 @NotNull Integer quantity,
                 @NotNull LocalDateTime createDateTime,
                 @NotNull LocalDateTime lastUpdatedDateTime) {
        super.setId(id);
        this.name = name;
        this.currency = currency;
        this.price = price;
        this.quantity = quantity;
        super.setCreateDateTime(createDateTime);
        super.setLastUpdatedDateTime(lastUpdatedDateTime);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(super.getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Stock.class.getSimpleName() + "[", "]")
                .add("id=" + super.getId())
                .add("name='" + name + "'")
                .add("currency='" + currency + "'")
                .add("price=" + price)
                .add("quantity=" + quantity)
                .add("createDateTime=" + super.getCreateDateTime())
                .add("lastUpdatedDateTime=" + super.getLastUpdatedDateTime())
                .toString();
    }

    /**
     * Covert the class into a comma-delimited representation before save into
     * persistence store.
     *
     * @return a CSV line representation of the class attributes as line of a
     * file.
     */
    @NotNull
    public String toCSV() {
        DateTimeFormatter formatter = CONST_DEFAULT_DATETIME_FORMAT.instance(DateTimeFormatter.class);
        return new StringJoiner(",")
                .add(super.getId() != null ? super.getId().toString() : "null")
                .add(name)
                .add(currency)
                .add(valueOf(price))
                .add(valueOf(quantity))
                .add(super.getCreateDateTime() != null
                        ? super.getCreateDateTime().format(formatter)
                        : "null")
                .add(super.getLastUpdatedDateTime() != null
                        ? super.getLastUpdatedDateTime().format(formatter)
                        : "null")
                .toString();
    }
}
