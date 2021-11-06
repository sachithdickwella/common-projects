package com.payconiq.geektastic.util.store;

import com.payconiq.geektastic.entity.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.payconiq.geektastic.util.AppConstants.CONST_DEFAULT_DATETIME_FORMAT;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

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
     * Static final Log4j logging instance for {@code InMemoryStoreConfig} class.
     */
    private static final Logger LOGGER = LogManager.getLogger(StockStore.class);
    /**
     * Encapsulated base {@link Map} to implement store behaviour with common
     * CRUD operations.
     */
    private Map<UUID, Stock> store;
    /**
     *
     */
    @Value("${classpath:store/stock-store.csv}")
    private ClassPathResource resource;

    /**
     * Lifecycle method to perform operation during bean creation.
     */
    @PostConstruct
    @Override
    public void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            store = reader.lines()
                    .skip(1)
                    .parallel()
                    .map(line -> line.split(","))
                    .map(this::lineToStock)
                    .collect(Collectors.toMap(Stock::id, stock -> stock));
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
        Files.write(resource.getFile().toPath(),
                store.values()
                        .stream()
                        .map(Stock::toString)
                        .toList(),
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Insert operation handler for the store. Only handle single entry
     * at once.
     *
     * @param value to be inserted to the {@link #store} representation
     *              of {@link HashMap}.
     * @return {@code int} value of how many entries affected (inserted)
     * and if this operation is a success, this will always return 1.
     */
    @Override
    public int insert(@NotNull Stock value) {
        return 0;
    }

    /**
     * Update operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param value to be updated to the {@link #store} representation
     *              of {@link HashMap}.
     * @return {@code int} value of how many entries affected (updated)
     * and if this operation is a success, this will always return 1.
     */
    @Override
    public int update(@NotNull Stock value) {
        return 0;
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
        return null;
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
        DateTimeFormatter formatter = CONST_DEFAULT_DATETIME_FORMAT.instance(DateTimeFormatter.class);
        return new Stock(
                UUID.fromString(line[0]),
                line[1],
                line[2],
                parseDouble(line[3]),
                parseInt(line[4]),
                LocalDateTime.parse(line[5], formatter),
                LocalDateTime.parse(line[6], formatter));
    }
}
