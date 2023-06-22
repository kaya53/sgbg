package com.sgbg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveToken(Long userId, String type, String token, Long expires) {
        String key = type + ":" + token;
        redisTemplate.opsForValue().set(key, Long.toString(userId));
        redisTemplate.expire(key, expires, TimeUnit.SECONDS);
    }

    public String getUserIdByToken(String type, String token) {
        String key = type + ":" + token;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void updateToken(Long userId, String type, String token, Long expires) {
        if (getUserIdByToken(type, token) != null) {
            deleteToken(type, token);
        }
        saveToken(userId, type, token, expires);
    }

    public void deleteToken(String type, String token) {
        String key = type + ":" + token;
        if (getUserIdByToken(type, token) != null) {
            redisTemplate.delete(key);
        }
    }
}
