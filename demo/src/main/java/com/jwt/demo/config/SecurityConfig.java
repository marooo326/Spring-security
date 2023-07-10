package com.jwt.demo.config;

import jakarta.servlet.http.HttpFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final CorsConfigurationSource source;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(source))
                // 세션 사용 안함 (Stateless Server로)
                .sessionManagement(manage-> manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize-> authorize
                        .requestMatchers("/api/v1/user/**")
                        .hasAnyRole("USER","ADMIN","MANAGER")

                        .requestMatchers("/api/v1/manager/**")
                        .hasAnyRole("ADMIN","MANAGER")

                        .requestMatchers("/api/v1/admin/**")
                        .hasAnyRole("ADMIN")
                        .anyRequest().permitAll())

                .build();
    }
}
