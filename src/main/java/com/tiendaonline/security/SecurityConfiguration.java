package com.tiendaonline.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/productos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/clientes/**").permitAll()
                // Si no se ha configurado ninguna clase Advicer para manejar
                // las excepciones desde los endpoints de la API, entonces
                // Spring Boot usarar el que tiene por defecto, que esta unido
                // al endopoint "/error", el cual tiene que configurarse como
                // accesible sin autentcación, si no el cliente no recibira
                // ninguna especie de mensaje de error
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            ).httpBasic(httpBasic -> {});


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
        .username("admin")
        .password(passswordEncoder().encode("password123"))
        .roles("ADMIN")
        .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public BCryptPasswordEncoder passswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
