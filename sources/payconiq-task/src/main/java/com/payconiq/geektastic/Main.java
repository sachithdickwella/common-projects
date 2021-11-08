package com.payconiq.geektastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entrypoint of the program which contains the {@code main(String[])}
 * to start the application.
 *
 * @version 1.0.0
 */
@EnableScheduling
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
