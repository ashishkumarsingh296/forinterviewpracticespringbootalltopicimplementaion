package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String key, int maxRequests, int timeWindowSeconds) {
        String redisKey = "rate_limit:" + key;
        Long requests = redisTemplate.opsForValue().increment(redisKey);

        if (requests != null && requests == 1) {
            redisTemplate.expire(redisKey, Duration.ofSeconds(timeWindowSeconds));
        }

        return requests != null && requests <= maxRequests;
    }
}
