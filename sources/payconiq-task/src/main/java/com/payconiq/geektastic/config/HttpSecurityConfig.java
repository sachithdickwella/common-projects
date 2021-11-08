package com.payconiq.geektastic.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

/**
 * Basic web security configuration for application with in-memory
 * credential manager.
 *
 * @version 1.0.0
 */
@PropertySource("classpath:custom-config.properties")
@Configuration
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

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
     * Any endpoint that requires defense against common vulnerabilities can be specified
     * here, including public ones. See {@link HttpSecurity#authorizeRequests} and the
     * `permitAll()` authorization rule for more details on public endpoints.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/api")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /**
     * In-memory user details configurations with basic username and password authentication.
     *
     * @param encoder instance of {@link PasswordEncoder} to encode password to secure format.
     * @return an instance of {@link UserDetailsService} including the username, password and
     * roles if available.
     */
    @Bean
    public UserDetailsService userDetailsService(@NotNull PasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username(username)
                .password(password)
                .passwordEncoder(encoder::encode)
                .authorities(List.of())
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Bean context binding of {@link PasswordEncoder} with {@link BCryptPasswordEncoder}
     * to encode user credentials to secure format.
     *
     * @return an instance of {@link BCryptPasswordEncoder} to bind with {@code BeanContext}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
