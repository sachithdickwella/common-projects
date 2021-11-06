package com.payconiq.geektastic.util.store;

import com.payconiq.geektastic.entity.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.payconiq.geektastic.util.AppConstants.CONST_DEFAULT_DATETIME_FORMAT;
import static com.payconiq.geektastic.util.EntityStatus.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.joining;

/**
 * Implementation of {@link Store} for {@link Stock}s, which provides
 * basic CURD operations for stock handling.
 *
 * @version 1.0.0
 */
@PropertySource("classpath:store-config.properties")
@Component("stockStore")
public class StockStore implements Store<UUID, Stock> {

    /**
     * Static final Log4j logging instance for {@code StockStore} class.
     */
    private static final Logger LOGGER = LogManager.getLogger(StockStore.class);
    /**
     * Encapsulated base {@link Map} to implement store behaviour with common
     * CRUD operations.
     */
    private Map<UUID, Stock> store;
    /**
     * Stock datastore file inject into a {@link ClassPathResource} instance to
     * retrieve data in it.
     */
    @Value("${classpath:store/stock-store.csv}")
    private ClassPathResource resource;
    /**
     * Locking time period from the properties as an {@link Integer} value.
     */
    @Value("#{T(Integer).parseInt('${app.datasource.lock.time}')}")
    private int lockTime;
    /**
     * Locking time period unit from the properties as a {@link ChronoUnit} value.
     */
    @Value("#{T(java.time.temporal.ChronoUnit).valueOf('${app.datasource.lock.time.unit}'.toUpperCase())}")
    private ChronoUnit lockTimeUnit;

    /**
     * Lifecycle method to perform operation during bean creation.
     */
    @PostConstruct
    @Override
    public void init() {
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            store = reader.lines()
                    .skip(1)
                    .parallel()
                    .map(line -> line.split(","))
                    .map(this::lineToStock)
                    .collect(Collectors.toMap(Stock::getId, stock -> stock));
        } catch (IOException ex) {
            LOGGER.error("StockStore init is failed with {} data store: {}", resource, ex);
        }
    }

    /**
     * Lifecycle method to perform operation during bean destruction.
     */
    @PreDestroy
    @Override
    public void dispose() throws IOException {
        var target = resource.getFile().toPath();
        Files.writeString(target, Stream.of(
                                "Id",
                                "Name",
                                "Currency",
                                "Price",
                                "Quantity",
                                "CreateDateTime",
                                "LastUpdatedDateTime")
                        .collect(joining(",", "", "\n")),
                StandardOpenOption.TRUNCATE_EXISTING);

        Files.write(target, store.values()
                .stream()
                .map(Stock::toString).toList(), StandardOpenOption.APPEND);
    }

    /**
     * Insert operation handler for the store. Only handle single entry
     * at once.
     *
     * @param stock to be inserted to the {@link #store} representation
     *              of {@link HashMap}.
     */
    @Override
    public void insert(@NotNull Stock stock) {
        if (store.containsKey(stock.getId())) {
            stock.setStatus(DUPLICATE);
        } else {
            stock.setId(UUID.randomUUID());

            var now = LocalDateTime.now();
            stock.setCreateDateTime(now);
            stock.setLastUpdatedDateTime(now);
            stock.setStatus(NEW);

            store.put(stock.getId(), stock);
        }
    }

    /**
     * Update operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param stock to be updated to the {@link #store} representation
     *              of {@link HashMap}.
     */
    @Override
    public void update(@NotNull Stock stock) {
        if (store.containsKey(stock.getId())) {
            if (isLocked(stock)) {
                stock.setStatus(LOCKED);
            } else {
                stock.setLastUpdatedDateTime(LocalDateTime.now());
                stock.setStatus(UPDATED);

                store.put(stock.getId(), stock);
            }
        } else {
            stock.setStatus(NOT_FOUND);
        }
    }

    /**
     * Delete operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param key of the value which needs to be removed from the {@link #store}.
     * @return {@link Stock} value which recently deleted from the store. Return
     * value is wrapped with {@link Optional} hence possibility of null value.
     */
    @NotNull
    @Override
    public Optional<Stock> delete(@NotNull UUID key) {
        return Optional.ofNullable(store.remove(key));
    }

    /**
     * Select operation handler for the store. Only handle single entry at a time
     * by the key.
     *
     * @param key of the value which needs to be fetched from the {@link #store}.
     * @return an instance of {@link Stock} respective to the {@link UUID} key
     * provided. Return value is wrapped with {@link Optional} hence possibility
     * of null value.
     */
    @NotNull
    @Override
    public Optional<Stock> select(@NotNull UUID key) {
        return Optional.ofNullable(store.get(key));
    }

    /**
     * Select operation handler for the store. Fetch all the entries of the store
     * at a time.
     *
     * @return a {@link List} of {@link Stock}s available in the store implementation.
     */
    @NotNull
    @Override
    public List<Stock> select() {
        return store.values().stream().toList();
    }

    /**
     * Supplementary method to convert decoded line of details of a file to an instance
     * of {@link Stock} and return to the upstream.
     *
     * @param line an array of {@link String} to be extract details of the {@code line}.
     * @return an instance of {@link Stock} with the details of {@code line} parameter.
     */
    @NotNull
    private Stock lineToStock(@NotNull String[] line) {
        var formatter = CONST_DEFAULT_DATETIME_FORMAT.instance(DateTimeFormatter.class);

        String price = line[3];
        String quantity = line[4];

        return new Stock(
                UUID.fromString(line[0]),
                line[1],
                line[2],
                !price.isBlank() && !price.equalsIgnoreCase("null") ? parseDouble(line[3]) : 0.0D,
                !quantity.isBlank() && !price.equalsIgnoreCase("null") ? parseInt(line[4]) : 0,
                LocalDateTime.parse(line[5], formatter),
                LocalDateTime.parse(line[6], formatter));
    }

    /**
     * Determine if the {@link Stock}'s last updated date time is within the locking
     * period and return {@code true}, else return {@code false}.
     *
     * @param stock instance of {@link Stock} to be validated for locking.
     * @return a {@code boolean} value whether the stock is locked or not.
     */
    private boolean isLocked(@NotNull Stock stock) {
        return LocalDateTime.now().isAfter(stock.getLastUpdatedDateTime().plus(lockTime, lockTimeUnit));
    }
}
