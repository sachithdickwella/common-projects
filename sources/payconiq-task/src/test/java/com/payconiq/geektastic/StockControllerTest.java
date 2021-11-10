package com.payconiq.geektastic;

import com.payconiq.geektastic.config.CommonTestConfig;
import com.payconiq.geektastic.controller.StockController;
import com.payconiq.geektastic.entity.Stock;
import com.payconiq.geektastic.util.pojo.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for {@link StockController} rest-controller to cover main
 * HTTP scenarios.
 *
 * @version 1.0.0
 */
@PropertySource("classpath:custom-config.properties")
@DisplayName("StockController Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(CommonTestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockControllerTest {

    /**
     * Valid UUID as a {@link String}, but missing in the datastore to test some
     * negative scenarios.
     */
    private static final String MISSING_ID = "de95ca16-6e21-4fe8-a8db-9ca527911aa1";
    /**
     * Username for application authentication.
     */
    @Value("${app.auth.credential.username}")
    private String username;
    /**
     * Password for application authentication.
     */
    @Value("${app.auth.credential.password}")
    private String password;
    /**
     * Random port number from SpringBoot web
     * environment, just for testing.
     */
    @LocalServerPort
    private int port;
    /**
     * HTTP client instance from SpringBoot web
     * environment for HTTP calls.
     */
    @Autowired
    private TestRestTemplate restTemplate;
    /**
     * {@link List} of {@link Stock} items to juggle throughout testing.
     */
    private List<Stock> stocks;
    /**
     * Object of {@link URI} to shared between thread for path assertion.
     */
    private URI uri;
    /**
     * Instance of {@link UUID} to check same stock between multiple test
     * scenarios and finally test lock scenarios.
     */
    private UUID idToLock;
    /**
     * Instance of {@link UUID} to check same stock between multiple test
     * scenarios and finally test delete scenario.
     */
    private UUID idToDelete;

    /**
     * Setup pre-testing environment for {@link StockController} testing.
     * <p>
     * Initialize local member {@link #stocks} with dummy {@link Stock}
     * instances
     */
    @BeforeAll
    void setup() {
        stocks = List.of(
                new Stock("Item 200 (test)", "USD", 10.25, 840),
                new Stock("Item 210 (test)", "LKR", 12.45, 1500),
                new Stock("Item 220 (test)", "EUR", 8.55, 900),
                new Stock("Item 230 (test)", "EUR", 100.75, 800),
                new Stock("Item 240 (test)", "USD", 5.50, 10000)
        );
    }

    /**
     * Test case for positive create scenario with basic authentication enabled when
     * correct payload is provided.
     */
    @DisplayName("Should pass if created with auth JSON content")
    @Order(1)
    @Test
    void creatShouldPassIfCreatedWithAuthJsonContent() {
        var response = invokeEndpoint("/api/stocks",
                HttpMethod.POST,
                new HttpEntity<>(stocks.get(0)),
                null,
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Stock\\screated\\swith\\sid\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}" +
                        "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(1, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.payload().size());

        idToLock = body.payload().get(0).getId();
    }

    /**
     * Test case for negative create scenario if an existing {@code 'id'} provided in the
     * payload with basic authentication enabled.
     */
    @DisplayName("Should fail if created with auth JSON content with existing id")
    @Order(2)
    @Test
    void creatShouldFailIfCreatedWithAuthJsonContentWithExistingId() {
        var invalidStock = stocks.get(0);
        invalidStock.setId(idToLock);

        var response = invokeEndpoint("/api/stocks",
                HttpMethod.POST,
                new HttpEntity<>(invalidStock),
                null,
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Stock\\sid\\sprovided\\salready\\sexists:\\s[a-fA-F0-9]{0,8}" +
                        "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}\\.\\s'Id'\\sshould\\sbe\\sremoved$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(0, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.payload().size());
    }

    /**
     * Test case for positive get all scenario with basic authentication enabled.
     */
    @DisplayName("Should retrieve all items with auth JSON content")
    @Order(3)
    @Test
    void shouldRetrieveAllIfAuthJsonContent() {
        var response = invokeEndpoint("/api/stocks",
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<Response<Stock>>() {
                });

        var body = response.getBody();
        var pattern = "^Number\\sof\\sstocks\\sretrieved:\\s(\\d+)$";

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches(pattern),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(body.count(), body.payload().size(),
                "Counts don't match in response.count and payload.size()");

        var countInMessage = Pattern.compile(pattern).matcher(body.message())
                .results()
                .filter(m -> m.groupCount() > 0)
                .mapToInt(m -> parseInt(m.group(1)))
                .findFirst()
                .orElse(0);
        assertEquals(body.count(), countInMessage,
                "Counts don't match in response.count and response.message[count]");
    }

    /**
     * Test case for positive get single scenario if an existing {@code 'id'} provided in the
     * path variable with basic authentication enabled.
     */
    @DisplayName("Should retrieve single items with auth JSON content by id")
    @Order(4)
    @Test
    void shouldRetrieveSingleIfAuthJsonContentById() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.GET,
                null,
                Map.of("id", idToLock),
                new ParameterizedTypeReference<Response<Stock>>() {
                });

        var body = response.getBody();
        var pattern = "^Number\\sof\\sstocks\\sretrieved\\sfor\\sid\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}" +
                "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}':\\s(\\d+)$";

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), body.status());
        assertEquals(uri.getPath(), body.path(),
                "Expected path doesn't match with actual '%s'".formatted(body.path()));
        assertNotNull(body.message());
        assertTrue(body.message().matches(pattern),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.count(),
                "'body.count()' should be 1");
        assertEquals(body.count(), body.payload().size(),
                "Counts don't match in response.count and payload.size()");

        var countInMessage = Pattern.compile(pattern).matcher(body.message())
                .results()
                .filter(m -> m.groupCount() > 0)
                .mapToInt(m -> parseInt(m.group(1)))
                .findFirst()
                .orElse(0);
        assertEquals(body.count(), countInMessage,
                "Counts don't match in response.count and response.message[count]");
    }

    /**
     * Test case for negative get single scenario if a not existing {@code 'id'} provided in the
     * path variable with basic authentication enabled.
     */
    @DisplayName("Should retrieve empty items with auth JSON content by missing id")
    @Order(5)
    @Test
    void shouldRetrieveEmptyIfAuthJsonContentByMissingId() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.GET,
                null,
                Map.of("id", MISSING_ID),
                new ParameterizedTypeReference<Response<Stock>>() {
                });

        var body = response.getBody();
        var pattern = "^No\\sstock\\sis\\savailable\\sfor\\sthe\\sid\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}" +
                "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'\\sto\\sretrieve$";

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), body.status());
        assertEquals("/api/stocks/%s".formatted(MISSING_ID), body.path(),
                "Expected path doesn't match with actual '%s'".formatted(body.path()));
        assertNotNull(body.message());
        assertTrue(body.message().matches(pattern),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(0, body.count(),
                "'body.count()' should be 1");
        assertEquals(body.count(), body.payload().size(),
                "Counts don't match in response.count and payload.size()");
    }

    /**
     * Test case for positive update single scenario if an existing {@code 'id'} provided in the
     * path variable with basic authentication enabled and payload has valid values.
     */
    @DisplayName("Should pass if updated with auth JSON content")
    @Order(6)
    @Test
    void updateShouldPassIfUpdatedWithAuthJsonContent() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(stocks.get(1)),
                Map.of("id", idToLock),
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Stock\\smodified\\swith\\sid\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}" +
                        "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(1, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.payload().size());
        assertTrue(equals(stocks.get(1), body.payload().get(0)),
                "Modified stock not match with update request");
    }

    /**
     * Test case for negative update single scenario if a not existing {@code 'id'} provided
     * in the path variable with basic authentication enabled and payload has valid values.
     */
    @DisplayName("Should fail if updated with auth JSON content by missing id")
    @Order(7)
    @Test
    void updateShouldFailIfUpdatedWithAuthJsonContentWithMissingId() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(stocks.get(1)),
                Map.of("id", MISSING_ID),
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Cannot\\sfind\\sthe\\sstock\\sfor\\sid\\s'[a-fA-F0-9]{0,8}" +
                        "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'" +
                        "\\.\\sStock\\snot\\smodified$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(0, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(0, body.payload().size());
    }

    /**
     * Test case for negative update single scenario if an update happened recently and stock's
     * been locked for modification with basic authentication enabled and payload has valid values
     * and {@code 'id'} provided
     */
    @DisplayName("Should fail if updated with auth JSON content due to lock")
    @Order(8)
    @Test
    void tryUpdateShouldFailIfUpdatedWithAuthJsonContentDueToLock() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(stocks.get(2)),
                Map.of("id", idToLock),
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Update\\sfailed\\son\\slocked\\sstock\\sfor\\sid\\s'" +
                        "[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'" +
                        "\\.\\sTry\\sagain\\slater$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(0, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.payload().size());
    }

    /**
     * Test case for negative update single scenario if an update happened recently and stock's
     * been locked, and locker has been full of locked items and try to perform an update with
     * basic authentication enabled and payload has valid values and {@code 'id'} provided.
     */
    @DisplayName("Should fail if updated with auth JSON content due to locker full")
    @Order(9)
    @Test
    void tryUpdateShouldFailIfUpdatedWithAuthJsonContentDueLockerFull() {
        var createResponse = invokeEndpoint("/api/stocks",
                HttpMethod.POST,
                new HttpEntity<>(stocks.get(3)),
                null,
                new ParameterizedTypeReference<>() {
                });

        var createBody = createResponse.getBody();

        assertNotNull(createBody, "Response body content is null");
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), createBody.status());
        assertEquals(uri.getPath(), createBody.path());
        assertEquals(1, createBody.count());
        assertNotNull(createBody.payload(), "Response body payload is null");
        assertEquals(1, createBody.payload().size());

        idToDelete = createBody.payload().get(0).getId();

        var updateResponse = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(stocks.get(4)),
                Map.of("id", idToDelete),
                new ParameterizedTypeReference<>() {
                });

        var updateBody = updateResponse.getBody();

        assertNotNull(updateBody, "Response body content is null");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, updateResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), updateBody.status());
        assertEquals(uri.getPath(), updateBody.path());
        assertNotNull(updateBody.message());
        assertTrue(updateBody.message().matches("^Update\\scannot\\sacquire\\slock\\son\\sstock\\sfor\\sid" +
                        "\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'" +
                        "\\.\\sTry\\sagain\\slater$"),
                "Response message '%s' is not valid for the status: %d".formatted(
                        updateBody.message(),
                        updateBody.status()));
        assertEquals(0, updateBody.count());
        assertNotNull(updateBody.payload(), "Response body payload is null");
        assertEquals(1, updateBody.payload().size());
    }

    /**
     * Test case for positive delete single scenario if an existing {@code 'id'} provided in the
     * path variable with basic authentication enabled.
     */
    @DisplayName("Should pass if deleted with auth JSON content")
    @Order(10)
    @Test
    void deleteShouldPassIfDeletedWithAuthJsonContent() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.DELETE,
                null,
                Map.of("id", idToDelete),
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(HttpStatus.ACCEPTED.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Stock\\sdeleted\\sfor\\sid\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}" +
                        "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(1, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.payload().size());
    }

    /**
     * Test case for negative delete single scenario if a not existing {@code 'id'} provided
     * in the path variable with basic authentication enabled.
     */
    @DisplayName("Should fail if delete with auth JSON content by missing id")
    @Order(11)
    @Test
    void deleteShouldFailIfDeletedWithAuthJsonContentWithMissingId() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.DELETE,
                null,
                Map.of("id", MISSING_ID),
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Cannot\\sfind\\sthe\\sstock\\sfor\\sid\\s'[a-fA-F0-9]{0,8}" +
                        "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'\\.\\sStock" +
                        "\\snot\\sdeleted$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(0, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(0, body.payload().size());
    }

    /**
     * Test case for negative delete single scenario if stock's been locked for modification
     * with basic authentication enabled and valid {@code 'id'} provided
     */
    @DisplayName("Should fail if deleted with auth JSON content due to lock")
    @Order(12)
    @Test
    void tryDeleteShouldFailIfDeletedWithAuthJsonContentDueToLock() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.DELETE,
                null,
                Map.of("id", idToLock),
                new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), body.status());
        assertEquals(uri.getPath(), body.path());
        assertNotNull(body.message());
        assertTrue(body.message().matches("^Delete\\sfailed\\son\\slocked\\sstock\\sfor\\sid\\s'[a-fA-F0-9]" +
                        "{0,8}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'\\.\\sTry" +
                        "\\sagain\\slater$"),
                "Response message '%s' is not valid for the status: %d".formatted(body.message(), body.status()));
        assertEquals(0, body.count());
        assertNotNull(body.payload(), "Response body payload is null");
        assertEquals(1, body.payload().size());
    }

    /**
     * Test case for negative get single scenario if an invalid {@code 'id'} provided in the
     * path variable with basic authentication enabled.
     */
    @DisplayName("Should fail single items with auth JSON content by invalid id")
    @Order(13)
    @Test
    void shouldFailSingleIfAuthJsonContentByInvalidId() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.GET,
                null,
                Map.of("id", 1),
                new ParameterizedTypeReference<Response<Stock>>() {
                });

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.status());
        assertEquals(uri.getPath(), body.path(),
                "Expected path doesn't match with actual '%s'".formatted(body.path()));
        assertNotNull(body.message());
        assertNull(body.payload(), "Response body payload is not null");
        assertNull(body.count(), "Response count is not null");
    }

    /**
     * Test case for negative any scenario if not authenticated.
     */
    @DisplayName("Should fail any without authentication")
    @Order(14)
    @Test
    void shouldFailAnyWithoutAuthentication() {
        var response = invokeEndpoint("/api/stocks",
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<Response<Stock>>() {
                }, false);

        var body = response.getBody();

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.status());
        assertEquals(uri.getPath(), body.path(),
                "Expected path doesn't match with actual '%s'".formatted(body.path()));
        assertNull(body.payload(), "Response body payload is not null");
        assertNull(body.count(), "Response count is not null");
    }

    /**
     * Common handling method to invoke mock server endpoints for testing based on given
     * parameters.
     *
     * @param path          {@link String} representation of the endpoint on the server.
     * @param httpMethod    {@link HttpMethod} to denote which http method to use during
     *                      invoke.
     * @param entity        instance of {@link T} wrapped with {@link HttpEntity} to send
     *                      out as the request body.
     * @param uriVariables  instance of {@link Map} to replace any placeholder in the path
     *                      with path variable values.
     * @param typeReference instance of {@link ParameterizedTypeReference} to represent
     *                      generic {@link Response} type.
     * @param authEnabled   instance of {@code boolean} vararg to make authenticated and not
     *                      authenticated requests.
     * @return an instance of {@link ResponseEntity} to the upstream which represents the
     * http response.
     */
    @NotNull
    private <T> ResponseEntity<Response<T>> invokeEndpoint(
            @NotNull String path,
            @NotNull HttpMethod httpMethod,
            @Nullable HttpEntity<T> entity,
            @Nullable Map<String, ?> uriVariables,
            @NotNull ParameterizedTypeReference<Response<T>> typeReference,
            boolean... authEnabled) {

        this.uri = UriComponentsBuilder.fromUriString("http://localhost")
                .port(port)
                .path(path)
                .buildAndExpand(uriVariables == null ? Map.of() : uriVariables)
                .toUri();

        var template = restTemplate;
        if (authEnabled.length == 0 || authEnabled[0]) template = template.withBasicAuth(username, password);

        return template.exchange(this.uri,
                httpMethod,
                entity,
                typeReference);
    }

    /**
     * Equals two {@link Stock} instances based on externally modifiable attributes and return
     * {@code true} if attributes are matching, else {@code false}.
     *
     * @param req instance of {@link Stock} from the request sending from test class.
     * @param res instance of {@link Stock} from the response coming in from the server.
     * @return whether the two {@link Stock} are matching or not. Return {@link true} if equals,
     * else {@code false}.
     */
    private boolean equals(@NotNull Stock req, @NotNull Stock res) {
        return req.getName().equals(res.getName())
                && req.getCurrency().equals(res.getCurrency())
                && req.getPrice().equals(res.getPrice())
                && req.getQuantity().equals(res.getQuantity());
    }
}
