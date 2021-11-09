package com.payconiq.geektastic.config;

import com.payconiq.geektastic.entity.Stock;
import com.payconiq.geektastic.util.LockingHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Provision common bean instances required by the test environment, or replace
 * the existing ones.
 *
 * @version 1.0.0
 */
@TestConfiguration
public class CommonTestConfig {

    /**
     * Bind new {@link LockingHandler} to override existing bean to reduce acceptable locking count.
     *
     * @return an instance of {@link LockingHandler} with locker capacity of 1 at a time.
     */
    @Bean
    public LockingHandler<Stock> getLockingHandler() {
        return new LockingHandler<>(1);
    }
}
