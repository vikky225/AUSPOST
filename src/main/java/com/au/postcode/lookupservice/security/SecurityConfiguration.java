package com.au.postcode.lookupservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
         http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/postcode-detail/**")
                 .hasAnyRole("USER","ADMIN")
                 .pathMatchers(HttpMethod.POST, "/api/postcode-detail/post/**")
                 .hasRole("ADMIN")
                 .pathMatchers(HttpMethod.PUT, "/api/postcode-detail/**")
                .hasRole("ADMIN")
                 .pathMatchers(HttpMethod.DELETE, "/api/postcode-detail/**")
                 .hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .httpBasic();

              return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
