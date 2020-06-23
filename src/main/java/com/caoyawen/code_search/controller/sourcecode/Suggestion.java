package com.caoyawen.code_search.controller.sourcecode;

import com.caoyawen.code_search.domain.sourcecode.SearchHistory;
import com.caoyawen.code_search.properties.SearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/source_code/suggestion")
public class Suggestion {
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private SearchProperties searchProperties;

    @Autowired
    public void setSearchProperties(SearchProperties searchProperties) {
        this.searchProperties = searchProperties;
    }

    @GetMapping("/search")
    public List<String> search(Authentication authentication, @RequestParam(value = "q", defaultValue = "") String query){
        final SearchHistory searchHistory = new SearchHistory(searchProperties, redisTemplate);
        final List<String> ret = searchHistory.getSearchText(authentication.getName());
        log.info("search suggestion about [{}] {}", query, ret);
        return ret;
    }
}
