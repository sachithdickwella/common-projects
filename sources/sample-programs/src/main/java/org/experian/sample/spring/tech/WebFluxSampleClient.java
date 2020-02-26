package org.experian.sample.spring.tech;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@EnableAutoConfiguration
public class WebFluxSampleClient {

    @Bean
    public WebClient getWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    public CommandLineRunner demo(WebClient client) {
        return args -> client.get()
                .uri("/temp")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Integer.class)
                .map(String::valueOf)
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebFluxSampleClient.class);
    }
}
