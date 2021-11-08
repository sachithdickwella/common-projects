package com.payconiq.geektastic.util.store;

import com.payconiq.geektastic.entity.Stock;
import com.payconiq.geektastic.util.LockingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.payconiq.geektastic.util.AppConstants.CONST_DEFAULT_DATETIME_FORMAT;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.joining;

/**
 * Implementation of {@link Store} for {@link Stock}s, which provides
 * basic CURD operations for stock handling.
 *
 * @version 1.0.0
 */
@Component("stockStore")
public class StockStore implements Store<UUID, Stock> {

    /**
     * Static final Log4j logging instance for {@code StockStore} class.
     */
    private static final Logger LOGGER = LogManager.getLogger(StockStore.class);
    /**
     * Instance of {@link LockingHandler}, wrapper for locker of entities to block
     * abusive updates.
     */
    private final LockingHandler<Stock> lockingHandler;
    /**
     *
     */
    private final ReentrantReadWriteLock lock;
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
     * Single-args constructor to initialize {@code ScheduledTask} with {@link LockingHandler}
     * instance which includes the locker.
     *
     * @param lockingHandler instance from the {@link java.beans.beancontext.BeanContext} to
     *                       initialize local member {@link #lockingHandler}.
     */
    public StockStore(@NotNull LockingHandler<Stock> lockingHandler) {
        this.lockingHandler = lockingHandler;
        this.lock = new ReentrantReadWriteLock(false);
    }

    /**
     * Lifecycle method to perform operation during bean creation.
     */
    @PostConstruct
    @Override
    public void init() {
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            var start = currentTimeMillis();
            store = reader.lines()
                    .skip(1)
                    .parallel()
                    .map(line -> line.split(","))
                    .map(this::lineToStock)
                    .collect(Collectors.toConcurrentMap(Stock::getId, stock -> stock));
            LOGGER.info("StockStore loading to memory is completed with {} dataset in {}ms",
                    resource,
                    currentTimeMillis() - start);
        } catch (IOException ex) {
            LOGGER.error("StockStore init is failed with resource: %s".formatted(resource), ex);
        }
    }

    /**
     * Lifecycle method to perform operation during bean destruction.
     */
    @PreDestroy
    @Override
    public void dispose() {
        try {
            var start = currentTimeMillis();
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
                    .map(Stock::toCSV).toList(), StandardOpenOption.APPEND);
            LOGGER.info("StockStore backup is completed to {} with in-memory dataset in {}ms",
                    resource,
                    currentTimeMillis() - start);
        } catch (IOException ex) {
            LOGGER.error("StockStore backup is failed with resource: %s".formatted(resource), ex);
        }
    }

    /**
     * Insert operation handler for the store. Only handle single entry
     * at once.
     *
     * @param stock to be inserted to the {@link #store} representation
     *              of {@link HashMap}.
     * @return {@link Optional} value affected entry. if this operation
     * is failed, {@link Optional#empty()} will be called.
     */
    @Override
    public Optional<Stock> insert(@NotNull Stock stock) {
        if (stock.getId() != null && store.containsKey(stock.getId())) {
            return Optional.empty();
        } else {
            var now = LocalDateTime.now();
            stock.setId(UUID.randomUUID());
            stock.setCreateDateTime(now);
            stock.setLastUpdatedDateTime(now);
            stock.setLocked(null);
            stock.setLockFailed(null);

            store.put(stock.getId(), stock);
            return Optional.of(stock);
        }
    }

    /**
     * Update operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param id    of the value which needs to be updated in the store.
     * @param stock to be updated to the {@link #store} representation
     *              of {@link HashMap}.
     * @return {@link Optional} value affected entry. if this operation
     * is failed, {@link Optional#empty()} will be called.
     */
    @Override
    public Optional<Stock> update(@NotNull UUID id, @NotNull Stock stock) {
        var writeLock = lock.writeLock();
        try {
            writeLock.lock();

            var opStock = select(id);
            if (opStock.isPresent()) {
                var original = opStock.get();
                stock.setId(original.getId());
                stock.setCreateDateTime(original.getCreateDateTime());
                stock.setLastUpdatedDateTime(LocalDateTime.now());

                if (lockingHandler.contains(original)) {
                    stock.setLocked(true);
                } else {
                    if (lockingHandler.offer(stock)) {
                        stock.setLocked(null);
                        stock.setLockFailed(null);

                        store.put(id, stock);
                        LOGGER.info("Update lock acquired: {}", stock);
                    } else {
                        stock.setLocked(false);
                        stock.setLockFailed(true);
                    }
                }
                return Optional.of(stock);
            } else {
                return opStock;
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Delete operation handler for the store. Only handle single entry
     * at a time.
     *
     * @param id of the value which needs to be removed from the {@link #store}.
     * @return {@link Stock} value which recently deleted from the store. Return
     * value is wrapped with {@link Optional} hence possibility of null value.
     */
    @NotNull
    @Override
    public Optional<Stock> delete(@NotNull UUID id) {
        var opStock = select(id);
        if (opStock.isPresent()) {
            var stock = opStock.get();
            if (lockingHandler.contains(stock)) {
                stock.setLocked(true);
                return Optional.of(stock);
            } else {
                var removed = store.remove(id);
                if (removed != null) removed.setLocked(null);

                return Optional.ofNullable(removed);
            }
        } else {
            return Optional.empty();
        }
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

        var price = line[3];
        var quantity = line[4];

        return new Stock(
                UUID.fromString(line[0]),
                line[1],
                line[2],
                !price.isBlank() && !price.equalsIgnoreCase("null") ? parseDouble(line[3]) : 0.0D,
                !quantity.isBlank() && !price.equalsIgnoreCase("null") ? parseInt(line[4]) : 0,
                LocalDateTime.parse(line[5], formatter),
                LocalDateTime.parse(line[6], formatter));
    }
}
