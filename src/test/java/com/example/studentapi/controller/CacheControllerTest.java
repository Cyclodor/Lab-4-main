package com.example.studentapi.controller;

import com.example.studentapi.service.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CacheController.class)
class CacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacheService cacheService;

    @Test
    void getCacheInfo_ShouldReturnCacheSize() throws Exception {
        when(cacheService.size()).thenReturn(5);

        mockMvc.perform(get("/api/cache/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(5));
    }

    @Test
    void clearCache_ShouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(delete("/api/cache/clear"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cache cleared successfully"));

        verify(cacheService, times(1)).clear();
    }

    @Test
    void removeFromCache_ShouldReturnSuccessMessage() throws Exception {
        String key = "test_key";

        mockMvc.perform(delete("/api/cache/{key}", key))
                .andExpect(status().isOk())
                .andExpect(content().string("Key '" + key + "' removed from cache"));

        verify(cacheService, times(1)).remove(key);
    }
} 