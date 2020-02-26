package org.experian.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Sachith Dickwella
 */
@EnableWebFlux
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Value("classpath:test.properties")
    private Resource resource;

    @Bean("default-props")
    public Properties getProperties() throws IOException {
        final Properties props = new Properties();
        props.load(resource.getInputStream());

        System.out.println(props.getProperty("my.last.name"));

        return props;
    }
}
