package com.example.studentapi.controller;

import com.example.studentapi.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {
    
    @Autowired
    private CacheService cacheService;
    
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getCacheInfo() {
        Map<String, Object> info = new HashMap<>();
        int size = cacheService.size();
        info.put("size", size);
        logger.info("=== CACHE CONTROLLER ===");
        logger.info("Cache info requested. Current size: {}", size);
        logger.info("Returning: {}", info);
        return ResponseEntity.ok(info);
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCache() {
        cacheService.clear();
        logger.info("Cache cleared successfully");
        return ResponseEntity.ok("Cache cleared successfully");
    }
    
    @DeleteMapping("/{key}")
    public ResponseEntity<String> removeFromCache(@PathVariable String key) {
        cacheService.remove(key);
        logger.info("Key '{}' removed from cache", key);
        return ResponseEntity.ok("Key '" + key + "' removed from cache");
    }
} 