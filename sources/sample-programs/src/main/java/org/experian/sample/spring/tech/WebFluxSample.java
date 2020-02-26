package org.experian.sample.spring.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Sachith Dickwella
 */
@RestController
@EnableWebFlux
@EnableAutoConfiguration
public class WebFluxSample {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        SpringApplication.run(WebFluxSample.class, args);
    }

    @GetMapping(value = "/temp", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getTemperature() {
        int low = 0;
        int high = 50;
        return Flux.fromStream(Stream.generate(() -> RANDOM.nextInt(high - low) + low)
                .map(String::valueOf))
                .map(Integer::valueOf)
                .delayElements(Duration.ofSeconds(3));
    }
}
