package com.caoyawen.code_search.domain.sourcecode;

import com.caoyawen.code_search.domain.es.Query;
import com.caoyawen.code_search.domain.es.SearchResponseHandler;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class QuerySourceCode extends Query {
    public QuerySourceCode() {
        super();
        setIndex("source_code");
    }

    static class HandleSearchResponse implements SearchResponseHandler {
        List<SearchTableDetail> contents = new LinkedList<>();

        @Override
        public void handleResponse(SearchResponse response) {
            final SearchHits hits = response.getHits();
            int count = 1;
            for (SearchHit hit : hits) {
                final Map<String, Object> map = hit.getSourceAsMap();
                contents.add(SearchTableDetail.builder()
                        .fileName((String)map.get("fileName"))
                        .group((String) map.get("group"))
                        .project((String) map.get("project"))
                        .no(count++)
                        .build());
            }
        }
    }

    @Cacheable(cacheNames = {"source_code"}, key="#text")
    public List<SearchTableDetail> contain(String text){
        final HandleSearchResponse handle = new HandleSearchResponse();
        contain(text, "fileContent", handle);
        return handle.contents;
    }
}
