package com.jwt.demo.config;

import com.jwt.demo.config.auth.jwt.JwtAuthenticationFilter;
import com.jwt.demo.filter.MyFilter3;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final CorsConfigurationSource source;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .addFilterBefore(new MyFilter3(), SecurityContextHolderFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(source))
                .sessionManagement(manage -> manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함 (Stateless Server로)
                .formLogin(AbstractHttpConfigurer::disable) // form tag login 안함
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(new JwtAuthenticationFilter())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/user/**")
                        .hasAnyRole("USER", "ADMIN", "MANAGER")

                        .requestMatchers("/api/v1/manager/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers("/api/v1/admin/**")
                        .hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .build();
    }
}
