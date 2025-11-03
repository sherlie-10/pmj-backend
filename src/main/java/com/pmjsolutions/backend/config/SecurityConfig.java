package com.pmjsolutions.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Security + CORS configuration.
 *
 * - CORS origins read from property "frontend.urls" (comma-separated) or env FRONTEND_URLS.
 * - When allowCredentials=true you must supply explicit origins (no "*").
 * - For now we permit open access to specific enquiry endpoints (dev/initial), you can tighten this later.
 */
@Configuration
public class SecurityConfig {

    /**
     * Comma-separated list of frontend origins.
     * Example: http://localhost:3000,https://pmjlogisticssolutions.com
     */
    @Value("${frontend.urls:http://localhost:3000}")
    private String frontendUrls;

    private List<String> parseOrigins(String csv) {
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        List<String> allowedOrigins = parseOrigins(frontendUrls);

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsSource) throws Exception {
        http
            // disable CSRF for a stateless REST API or if you use tokens (enable if using cookies/sessions carefully)
            .csrf(csrf -> csrf.disable())
            // wire up the CorsConfigurationSource into the security filter chain
            .cors(cors -> cors.configurationSource(corsSource))
            // session management: default (you can switch to stateless)
            // .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // public endpoints for your enquiry form — adjust these to match your controllers
                    .requestMatchers("/api/enquiry/**", "/enquiry/**", "/actuator/**").permitAll()
                    // static resources, swagger etc if any - adjust as needed
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    // everything else requires authentication (change to permitAll() during dev if you want)
                    .anyRequest().authenticated()
            );

        return http.build();
    }
}
