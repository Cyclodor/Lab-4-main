package com.example.studentapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheServiceTest {

    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        cacheService = new CacheService();
    }

    @Test
    void putAndGet_ShouldWorkCorrectly() {
        String key = "test_key";
        String value = "test_value";

        cacheService.put(key, value);
        Object retrievedValue = cacheService.get(key);

        assertEquals(value, retrievedValue);
    }

    @Test
    void containsKey_ShouldReturnTrue_WhenKeyExists() {
        String key = "test_key";
        String value = "test_value";

        cacheService.put(key, value);
        boolean contains = cacheService.containsKey(key);

        assertTrue(contains);
    }

    @Test
    void containsKey_ShouldReturnFalse_WhenKeyDoesNotExist() {
        String key = "non_existent_key";
        boolean contains = cacheService.containsKey(key);

        assertFalse(contains);
    }

    @Test
    void remove_ShouldRemoveKey() {
        String key = "test_key";
        String value = "test_value";

        cacheService.put(key, value);
        cacheService.remove(key);
        boolean contains = cacheService.containsKey(key);

        assertFalse(contains);
    }

    @Test
    void clear_ShouldRemoveAllKeys() {
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value2");

        int sizeBefore = cacheService.size();
        cacheService.clear();
        int sizeAfter = cacheService.size();

        assertEquals(2, sizeBefore);
        assertEquals(0, sizeAfter);
    }

    @Test
    void size_ShouldReturnCorrectSize() {
        assertEquals(0, cacheService.size());

        cacheService.put("key1", "value1");
        assertEquals(1, cacheService.size());

        cacheService.put("key2", "value2");
        assertEquals(2, cacheService.size());
    }
} 