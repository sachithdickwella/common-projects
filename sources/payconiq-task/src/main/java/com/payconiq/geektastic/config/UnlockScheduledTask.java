package com.payconiq.geektastic.config;

import com.payconiq.geektastic.entity.Entity;
import com.payconiq.geektastic.util.LockingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Component to schedule background periodic worker to expire {@link Entity} instances
 * based on their {@link Entity#getLastUpdatedDateTime()} value, so the entity will be
 * available for update once again.
 *
 * @version 1.0.0
 */
@Component
public class UnlockScheduledTask {

    /**
     * Static final Log4j logging instance for {@code UnlockScheduledTask} class.
     */
    private static final Logger LOGGER = LogManager.getLogger(UnlockScheduledTask.class);
    /**
     * Instance of {@link LockingHandler}, wrapper for locker of entities to block
     * abusive updates.
     */
    private final LockingHandler<? super Entity> lockingHandler;
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
     * Single-args constructor to initialize {@code ScheduledTask} with {@link LockingHandler}
     * instance which includes the locker.
     *
     * @param lockingHandler instance from the {@link java.beans.beancontext.BeanContext} to
     *                       initialize local member {@link #lockingHandler}.
     */
    public UnlockScheduledTask(@NotNull LockingHandler<? super Entity> lockingHandler) {
        this.lockingHandler = lockingHandler;
    }

    /**
     * Scheduled worker method to execute scheduled task. Which is remove expired entities from
     * the locker in this case.
     * <p>
     * {@link java.util.stream.Stream} only traverse until the last expired entity is found in
     * locker {@link java.util.concurrent.BlockingDeque} despite how many entries available in
     * the queue to maximize the efficiency.
     */
    @Scheduled(fixedRate = 5, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void unlock() {
        lockingHandler.stream()
                .takeWhile(this::isExpired)
                .forEach(v -> LOGGER.info("Lock expired: {}", lockingHandler.poll()));
    }

    /**
     * Determine if the {@link Entity}'s last updated date time is within the locking
     * period and return {@code true}, else return {@code false}.
     *
     * @param entity instance of {@link Entity} to be validated for locking.
     * @return a {@code boolean} value whether the stock is locked or not.
     */
    private boolean isExpired(@NotNull Entity entity) {
        return LocalDateTime.now().isAfter(entity.getLastUpdatedDateTime().plus(lockTime, lockTimeUnit));
    }
}
