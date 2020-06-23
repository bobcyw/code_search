package com.caoyawen.code_search.controller.sourcecode;

import com.caoyawen.code_search.domain.es.Query;
import com.caoyawen.code_search.domain.sourcecode.QuerySourceCode;
import com.caoyawen.code_search.domain.sourcecode.SearchHistory;
import com.caoyawen.code_search.domain.sourcecode.SearchTableDetail;
import com.caoyawen.code_search.properties.SearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequestMapping("/source_code")
@Controller
public class Search {
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
    public String search(Model model, Authentication authentication, @RequestParam(value = "text", defaultValue = "") String text){
        model.addAttribute("text", text);
        if (!text.isEmpty()) {
            Query query = new Query();
            query.setIndex("source_code");
            final QuerySourceCode querySourceCode = new QuerySourceCode();
            final List<SearchTableDetail> contents = querySourceCode.contain(text);
            model.addAttribute("contents", contents);
            final SearchHistory searchHistory = new SearchHistory(searchProperties, redisTemplate);
            searchHistory.saveSearchText(text, authentication.getName());
        }else{
            model.addAttribute("contents", new LinkedList<SearchTableDetail>(
                    Collections.singletonList(SearchTableDetail.builder().no(-1).fileName("不存在").group("").project("").build())
            ));
        }
        return "/sourcecode/search";
    }

}
