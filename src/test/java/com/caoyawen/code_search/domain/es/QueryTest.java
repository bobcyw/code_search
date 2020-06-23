package com.caoyawen.code_search.domain.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.suggest.Suggest;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
class QueryTest {
    Query query = new Query();
    @Test
    void testQuery() {
        query.setIndex("source_code");
        query.contain("some-domain.com", "fileContent", (response)->{
            final SearchHits hits = response.getHits();
            log.info("hits:{}", hits.getTotalHits().value);
            final SearchHit[] searchHits = hits.getHits();
            for (SearchHit searchHit : searchHits) {
                final Map<String, Object> map = searchHit.getSourceAsMap();
                log.info("{} - {}/{}", map.get("fileName"), map.get("group"), map.get("project"));
            }
        });
    }

    @Test
    void testSuggestion() {
        query.setIndex("source_code");
        query.contain("ksyun", "fileContent", response -> {
            final Suggest suggest = response.getSuggest();
            final Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggest_file = suggest.getSuggestion("suggest_file");
            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry : suggest_file.getEntries()) {
                for (Suggest.Suggestion.Entry.Option option : entry) {
                    log.info("{}", option.getText().string());
                }
            }
        });
    }
}