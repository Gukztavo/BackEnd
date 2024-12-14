package com.example.projeto_api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf(csrf -> csrf.disable())
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/usuarios/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/logout").permitAll()

                        .requestMatchers(HttpMethod.POST,"/categorias").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/categorias/**").permitAll()





                        //-----------------------------ADMIN---------------------------------------
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT,"/usuarios/**").hasRole("admin")

                        .requestMatchers(HttpMethod.POST,"/categorias").hasRole("admin")
                        .requestMatchers(HttpMethod.GET,"/categorias/**").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT,"/categorias/**").hasRole("admin")
                        .requestMatchers(HttpMethod.GET,"/categorias/").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT,"/categorias/").hasRole("admin")



                        // Acesso para usuários autenticados
                        .requestMatchers(HttpMethod.GET, "/usuarios/{email}").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //Make the below setting as * to allow connection from any hos
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Utilizando NoOpPasswordEncoder para armazenar senhas em texto plano

        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
  }