package com.caoyawen.code_search.domain.es;

import org.elasticsearch.action.search.SearchResponse;

public interface SearchResponseHandler {
    void handleResponse(SearchResponse response);
}
