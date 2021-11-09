package com.payconiq.geektastic;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockControllerTest {

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
     * scenarios.
     */
    private UUID id;

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
     *
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

        id = body.payload().get(0).getId();
    }

    /**
     *
     */
    @DisplayName("Should fail if created with auth JSON content with existing id")
    @Order(2)
    @Test
    void creatShouldFailIfCreatedWithAuthJsonContentWithExistingId() {
        var invalidStock = stocks.get(0);
        invalidStock.setId(id);

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
     *
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
     *
     */
    @DisplayName("Should retrieve single items with auth JSON content by id")
    @Order(4)
    @Test
    void shouldRetrieveSingleIfAuthJsonContentById() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.GET,
                null,
                Map.of("id", id),
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
     *
     */
    @DisplayName("Should retrieve empty items with auth JSON content by wrong id")
    @Order(5)
    @Test
    void shouldRetrieveEmptyIfAuthJsonContentByWrongId() {
        var missingId = "de95ca16-6e21-4fe8-a8db-9ca527911aa1";
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.GET,
                null,
                Map.of("id", missingId),
                new ParameterizedTypeReference<Response<Stock>>() {
                });

        var body = response.getBody();
        var pattern = "^No\\sstock\\sis\\savailable\\sfor\\sthe\\sid\\s'[a-fA-F0-9]{0,8}-[a-fA-F0-9]{0,4}" +
                "-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,4}-[a-fA-F0-9]{0,12}'\\sto\\sretrieve$";

        assertNotNull(body, "Response body content is null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), body.status());
        assertEquals("/api/stocks/%s".formatted(missingId), body.path(),
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
     * @return an instance of {@link ResponseEntity} to the upstream which represents the
     * http response.
     */
    private <T> ResponseEntity<Response<T>> invokeEndpoint(
            @NotNull String path,
            @NotNull HttpMethod httpMethod,
            @Nullable HttpEntity<T> entity,
            @Nullable Map<String, ?> uriVariables,
            @NotNull ParameterizedTypeReference<Response<T>> typeReference) {

        this.uri = UriComponentsBuilder.fromUriString("http://localhost")
                .port(port)
                .path(path)
                .buildAndExpand(uriVariables == null ? Map.of() : uriVariables)
                .toUri();
        return restTemplate.withBasicAuth(username, password)
                .exchange(this.uri,
                        httpMethod,
                        entity,
                        typeReference);
    }

    /**
     *
     */
    @DisplayName("Should pass if updated with auth JSON content")
    @Order(6)
    @Test
    void updateShouldPassIfUpdatedWithAuthJsonContent() {
        var response = invokeEndpoint("/api/stocks/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(stocks.get(1)),
                Map.of("id", id),
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

        id = body.payload().get(0).getId();
    }
}
