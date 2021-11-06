package com.payconiq.geektastic.controller;

import com.payconiq.geektastic.entity.Stock;
import com.payconiq.geektastic.util.pojo.Response;
import com.payconiq.geektastic.util.store.Store;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * Controller to provision {@link Stock} related HTTP and subsequent CRUD operations.
 *
 * @version 1.0.0
 */
@RequestMapping(path = "/stocks")
@RestController
public class StockController implements ControllerFacade<Stock> {

    /**
     * Instance of {@link Store} to perform DB like transactions.
     */
    private final Store<UUID, Stock> store;

    /**
     * Single-args public constructor to initialize local instance
     * members with injected beans from bean context.
     *
     * @param store instance injected by bean context. Annotation
     *              {@link Qualifier} not mandatory since only one
     *              {@link Store} bean is registered.
     */
    public StockController(@Qualifier("stockStore") Store<UUID, Stock> store) {
        this.store = store;
    }

    /**
     * Method to create entity in the database or any other persistence store. If
     * the operation is success, would return newly created instance encapsulated
     * with {@link Response} class.
     *
     * @param stock   instance of {@link Stock} which needed to be stored in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> create(@NotNull Stock stock,
                                                           @NotNull HttpServletRequest request) {
        return null;
    }

    /**
     * Method to update entity in the database or any other persistence store. If
     * the operation is success, would return updated instance encapsulated with
     * {@link Response} class.
     *
     * @param id      instance of {@link String} which denotes the entity id in the
     *                store.
     * @param stock   instance of {@link Stock} which needed to be updated in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = {
            APPLICATION_XML_VALUE,
            APPLICATION_JSON_VALUE
    })
    @Override
    public @NotNull ResponseEntity<Response<Stock>> update(@NotNull @PathVariable String id,
                                                           @NotNull Stock stock,
                                                           @NotNull HttpServletRequest request) {
        return null;
    }

    /**
     * Method to fetch single entity from the database or any other persistence store.
     * If the operation is success, would return respective {@link Stock} instance
     * encapsulated with {@link Response} class.
     *
     * @param id      instance of {@link String} which denotes the entity id in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @GetMapping(path = "/{id}", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> single(@NotNull @PathVariable String id,
                                                           @NotNull HttpServletRequest request) {
        List<Stock> stocks = store.select(UUID.fromString(id))
                .stream()
                .toList();

        return ResponseEntity.ok(
                buildResponse(request,
                        HttpStatus.OK,
                        stocks.isEmpty()
                                ? "No stock is available for the id '%s'".formatted(id)
                                : "Number of stocks retrieved for id '%s' are %d".formatted(id, stocks.size()),
                        stocks));
    }

    /**
     * Method to fetch all the entities from the database or any other persistence store.
     * If the operation is success, would return all the instances encapsulated with
     * {@link Response} class.
     *
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @GetMapping(produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> all(@NotNull HttpServletRequest request) {
        List<Stock> stocks = store.select();

        return ResponseEntity.ok(
                buildResponse(request,
                        HttpStatus.OK,
                        "Number of stocks retrieved: %d".formatted(stocks.size()),
                        stocks));
    }

    /**
     * Method to delete single entity from the database or any other persistence store.
     * If the operation is success, would return deleted instance encapsulated with
     * {@link Response} class.
     *
     * @param id      instance of {@link String} which denotes the entity id in the store.
     * @param request instance of {@link HttpServletRequest} to derive request details.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @DeleteMapping(path = "/{id}", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> delete(@NotNull @PathVariable String id,
                                                           @NotNull HttpServletRequest request) {
        return null;
    }
}
