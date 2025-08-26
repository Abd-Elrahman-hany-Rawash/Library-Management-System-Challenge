package com.library.Library.Management.System.Challenge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configure ->
                        configure
                                // Author endpoints
                                .requestMatchers(HttpMethod.GET, "/api/authors/**")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.POST, "/api/authors")
                                .hasRole("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/api/authors/**")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/authors/**")
                                .hasRole("ADMINISTRATOR")
                                // Book endpoints
                                .requestMatchers(HttpMethod.GET, "/api/books/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/books")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/books/**")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/books/**")
                                .hasRole("ADMINISTRATOR")

                                // BorrowingTransaction endpoints
                                .requestMatchers(HttpMethod.POST, "/api/member/transactions/borrow")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/member/transactions/*/return")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/member/transactions/return/book/**")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.GET, "/api/member/transactions/member/**")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.GET, "/api/member/transactions/**")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/member/transactions/**")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/member/transactions/**")
                                .hasRole("ADMINISTRATOR")



                                // Category endpoints
                                .requestMatchers(HttpMethod.GET, "/api/categories/**")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.POST, "/api/categories")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/categories/**")
                                .hasAnyRole("ADMINISTRATOR", "LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/categories/**")
                                .hasRole("ADMINISTRATOR")


                                // Member endpoints
                                .requestMatchers(HttpMethod.GET, "/api/members/**")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.POST, "/api/members")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.PUT, "/api/members/**")
                                .hasAnyRole("ADMINISTRATOR", "STAFF", "LIBRARIAN")
                                .requestMatchers(HttpMethod.DELETE, "/api/members/**")
                                .hasRole("ADMINISTRATOR")

                                 // System Users
                                .requestMatchers(HttpMethod.GET, "/api/system-users/**").hasRole("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.POST, "/api/system-users").hasRole("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.PUT, "/api/system-users/**").hasRole("ADMINISTRATOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/system-users/**").hasRole("ADMINISTRATOR")

                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // basic auth
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
