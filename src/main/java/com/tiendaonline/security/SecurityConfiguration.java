package com.tiendaonline.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String matchAllClientesApi = "/clientes/**";
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(matchAllClientesApi).permitAll()
                // Si no se ha configurado ninguna clase Advicer para manejar
                // las excepciones desde los endpoints de la API, entonces
                // Spring Boot usarar el que tiene por defecto, que esta unido
                // al endopoint "/error", el cual tiene que configurarse como
                // accesible sin autentcación, si no el cliente no recibira
                // ninguna especie de mensaje de error
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            );


        return http.build();
    }
}
