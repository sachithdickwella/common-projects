package com.payconiq.geektastic.util;

import com.payconiq.geektastic.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

/**
 * Simple wrapper class for a {@link BlockingQueue} to handle locking function
 * of {@link Entity}ies with basic queue functions.
 *
 * @version 1.0.0
 */
@PropertySource("classpath:custom-config.properties")
@Component
public class LockingHandler<T extends Entity> {

    /**
     * Instance of {@link BlockingQueue} to manage locked entities in order and
     * feed to expire later.
     */
    private final BlockingQueue<T> locker;

    /**
     * Single-args constructor to initialize required member {@code lockerCapacity}
     * which will be the {@link LinkedBlockingQueue} initial and final capacity since
     * the queue size cannot be extended during runtime.
     *
     * @param lockerCapacity an {@code int} value to initialize {@link BlockingQueue}
     *                       with capacity which is injected by {@code ApplicationContext}
     *                       from property source.
     */
    public LockingHandler(@Value("#{T(Integer).parseInt('${app.datasource.locker.capacity}')}") int lockerCapacity) {
        locker = new LinkedBlockingQueue<>(lockerCapacity);
    }

    /**
     * Wrapper for {@link BlockingQueue#contains(Object)} method to find out if the
     * object looking for is available or not.
     *
     * @param entity {@link T} type value to look for in {@link BlockingQueue}.
     * @return if the entity is available in the queue or not. {@code true} if
     * available, else {@code false}.
     */
    public boolean contains(@NotNull T entity) {
        return locker.contains(entity);
    }

    /**
     * Wrapper for {@link BlockingQueue#offer(Object)} method to add the entity
     * in to the queue.
     *
     * @param entity {@link T} type value to add in to the {@link BlockingQueue}.
     * @return if the entity is successfully added in to the queue or not. {@code true}
     * if added successfully, else {@code false}, if the queue is full.
     */
    public boolean offer(@NotNull T entity) {
        return locker.offer(entity);
    }

    /**
     * Wrapper for {@link BlockingQueue#poll()} method to fetch the entity from the
     * queue.
     *
     * @return fetch the most recent element available at the head of the queue and
     * remove it from the queue. If the head is empty, return {@link null}.
     */
    @Nullable
    public T poll() {
        return locker.poll();
    }

    /**
     * Wrapper for {@link BlockingQueue#stream()} method to fetch the entity from the
     * queue in a {@link Stream}. This wouldn't modify the queue unless specified.
     *
     * @return {@link Stream} all the elements of the queue unless empty, upto terminal
     * operation.
     */
    @NotNull
    public Stream<T> stream() {
        return locker.stream();
    }
}
