package com.example.servicea.Config;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.cloud.openfeign.FeignClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {


    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Tỷ lệ lỗi 50%
                .slidingWindowSize(10) // Cửa sổ trượt 10 yêu cầu
                .waitDurationInOpenState(Duration.ofSeconds(10)) // Chờ 10 giây khi mở mạch
                .permittedNumberOfCallsInHalfOpenState(2) // Cho phép 2 yêu cầu trong trạng thái HALF-OPEN
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
    }


    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfig circuitBreakerConfig) {
        return CircuitBreakerRegistry.of(circuitBreakerConfig);
    }

    @Bean
    public RetryConfig retryConfig() {

        return RetryConfig.custom().maxAttempts(3)
                .waitDuration(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public RetryRegistry RetryRegistry(RetryConfig RetryConfig) {
        return RetryRegistry.of(RetryConfig);
    }

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom().
                limitForPeriod(1)
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry(RateLimiterConfig RetryRegistry) {
        return RateLimiterRegistry.of(RetryRegistry);
    }

}