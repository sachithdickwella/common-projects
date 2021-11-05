package com.payconiq.geektastic.config;

import com.payconiq.geektastic.entity.Stock;
import com.payconiq.geektastic.util.store.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * {@link Component} role class to provision in-memory data store during Spring
 * {@link java.beans.beancontext.BeanContext} initialization.
 *
 * @version 1.0.0
 */
@PropertySource("classpath:store-config.properties")
@Configuration
public class InMemoryStoreConfig {

    /**
     * Static final Log4j logging instance for {@code InMemoryStoreConfig} class.
     */
    private static final Logger LOGGER = LogManager.getLogger(InMemoryStoreConfig.class);
    /**
     *
     */
    @Value("${classpath:store/stock-store.csv}")
    private ClassPathResource storePath;
    /**
     *
     */
    private Map<UUID, Stock> store;

    /**
     *
     */
    @PostConstruct
    public void create() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(storePath.getInputStream()))) {
            store = reader.lines()
                    .skip(1)
                    .parallel()
                    .map(line -> line.split(","))
                    .map(line -> new Stock(
                            UUID.fromString(line[0]),
                            line[1],
                            line[2],
                            parseDouble(line[3]),
                            parseInt(line[4]),
                            LocalDateTime.now(),
                            LocalDateTime.now()

                    )).collect(Collectors.toMap(Stock::id, stock -> stock));
        } catch (IOException ex) {
            LOGGER.error("InMemoryStore init is failed with {} data store: {}", storePath, ex);
        }
    }

    /**
     *
     */
    @PreDestroy
    public void destroy() {

    }

    /**
     *
     */
    @Bean
    public Store<UUID, Stock> stockInMemoryStore() {
        return new Store();
    }
}
