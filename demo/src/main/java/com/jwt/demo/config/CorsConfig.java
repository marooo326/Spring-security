package com.jwt.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    @Primary
    public UrlBasedCorsConfigurationSource corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답할 때, json을 자바스크립트에서 처리할 수 있게 할지
        config.addAllowedOrigin("*");     // 모든 ip에 응답 허용
        config.addAllowedHeader("*");     // 모든 헤더에 응답 허용
        config.addAllowedMethod("*");     // 모든 요청에 응답 허용
        source.registerCorsConfiguration("/api/**",config);
        return source;
    }
}
