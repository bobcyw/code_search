package com.caoyawen.code_search.controller;

import com.caoyawen.code_search.domain.lab.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/lab")
public class Lab {
    RedisTemplate<String, String> redisTemplate;
    Keys keys;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    @GetMapping(value = "/cache")
    public String getCache(Model model, @RequestParam("key") String key){
        final String value = keys.getKey(key);
        model.addAttribute("value", value);
        model.addAttribute("key", key);
        final ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key+"-manual", value);
        return "lab/cache";
    }

    @GetMapping(value = "/blank")
    public String blank(Model model){
        model.addAttribute("text", "hello, world");
        return "lab/blank";
    }
}
