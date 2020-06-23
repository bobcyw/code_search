package com.caoyawen.code_search.domain.lab;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Keys {
    @Cacheable(cacheNames = {"cache3"}, key="#key")
    public String getKey(String key){
        return calculKey(key);
    }

    @CachePut(cacheNames = {"cache3"}, key="#key")
    public String calculKey(String key){
        log.info("calculKey {}", key);
        return key + "-value";
    }
}
