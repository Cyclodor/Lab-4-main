package com.example.studentapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    
    public void put(String key, Object value) {
        cache.put(key, value);
        logger.info("Cache put: key='{}', value size={}", key, (value != null ? "not null" : "null"));
        logger.info("Cache size after put: {}", cache.size());
    }
    
    public Object get(String key) {
        Object value = cache.get(key);
        logger.info("Cache get: key='{}', found={}", key, (value != null));
        return value;
    }
    
    public boolean containsKey(String key) {
        boolean contains = cache.containsKey(key);
        logger.info("Cache contains: key='{}', result={}", key, contains);
        return contains;
    }
    
    public void remove(String key) {
        cache.remove(key);
        logger.info("Cache remove: key='{}'", key);
        logger.info("Cache size after remove: {}", cache.size());
    }
    
    public void clear() {
        int sizeBefore = cache.size();
        cache.clear();
        logger.info("Cache clear: size before={}, after={}", sizeBefore, cache.size());
    }
    
    public int size() {
        int size = cache.size();
        logger.info("Cache size: {}", size);
        return size;
    }
} 