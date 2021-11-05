package com.payconiq.geektastic.controller;

import com.payconiq.geektastic.config.InMemoryStoreConfig;
import com.payconiq.geektastic.entity.Stock;
import com.payconiq.geektastic.util.pojo.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     *
     */
    private final InMemoryStoreConfig.Store store;

    /**
     *
     */
    public StockController(InMemoryStoreConfig.Store store) {
        this.store = store;
    }

    /**
     * Method to create entity in the database or any other persistence store. If
     * the operation is success, would return newly created instance encapsulated
     * with {@link Response} class.
     *
     * @param stock instance of {@link Stock} which needed to be stored in the store.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> create(@NotNull Stock stock) {
        return null;
    }

    /**
     * Method to update entity in the database or any other persistence store. If
     * the operation is success, would return updated instance encapsulated with
     * {@link Response} class.
     *
     * @param id    instance of {@link String} which denotes the entity id in the
     *              store.
     * @param stock instance of {@link Stock} which needed to be updated in the store.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = {
            APPLICATION_XML_VALUE,
            APPLICATION_JSON_VALUE
    })
    @Override
    public @NotNull ResponseEntity<Response<Stock>> update(@NotNull @PathVariable String id, @NotNull Stock stock) {
        return null;
    }

    /**
     * Method to fetch single entity from the database or any other persistence store.
     * If the operation is success, would return respective {@link Stock} instance
     * encapsulated with {@link Response} class.
     *
     * @param id instance of {@link String} which denotes the entity id in the store.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @GetMapping(path = "/{id}", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> single(@NotNull @PathVariable String id) {
        return null;
    }

    /**
     * Method to fetch all the entities from the database or any other persistence store.
     * If the operation is success, would return all the instances encapsulated with
     * {@link Response} class.
     *
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @GetMapping(produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<List<Stock>>> all() {
        return null;
    }

    /**
     * Method to delete single entity from the database or any other persistence store.
     * If the operation is success, would return deleted instance encapsulated with
     * {@link Response} class.
     *
     * @param id instance of {@link String} which denotes the entity id in the store.
     * @return an instance of {@link Response} wrapped with {@link ResponseEntity}.
     */
    @DeleteMapping(path = "/{id}", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    @Override
    public @NotNull ResponseEntity<Response<Stock>> delete(@NotNull @PathVariable String id) {
        return null;
    }
}
