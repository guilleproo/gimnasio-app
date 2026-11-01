package com.ejemplo.gimnasio.security;

import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.beans.factory.annotation.Value;


@Configuration 
public class SecurityConfig {
    
    @Value("${app.security.enabled:true}")
    private boolean securityEnabled; 

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws ExecutionException{

        if (!securityEnabled){
            http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(h -> h.frameOptions(f -> f.sameOrigin()));
            return http.build();
        }

        http
            .authorizeHttpRequests(auth -> auth 
                .requestMatchers("/login","/recuperar", "/h2-console/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/usuarios/nuevo", "/usuarios/guardar", "/usuarios/*/editar", "/usuarios/*/actualizar", "/usuarios/*/eliminar").hasRole("ENTRENADOR")
                .requestMatchers("/usuarios", "/usuarios/**").authenticated()
                .anyRequest().authenticated() 
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/usuarios", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(h -> h.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
