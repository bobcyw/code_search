package com.caoyawen.code_search.domain.es;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;

import java.io.IOException;

@Slf4j
@Data
public class Query {
    private String index;

    public void contain(String text, String defaultField, SearchResponseHandler handler){
        try (RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ))) {
            final SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.queryStringQuery(text).defaultField(defaultField)).from(0).size(200);
            final SearchRequest request = new SearchRequest();
            request.indices(index);
            request.source(builder);
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            handler.handleResponse(response);
        } catch (IOException e) {
            log.warn("meet exception", e);
        }
    }

    /*
    ES的suggest做不出来也不知道该咋用...,感觉需要整体设计一下
     */
    public void suggestion(String text, String defaultField, SearchResponseHandler handler){
        try (RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ))) {
            final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            final TermSuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion(defaultField).text(text);
            final SuggestBuilder suggestBuilder = new SuggestBuilder();
            suggestBuilder.addSuggestion("suggest_file", termSuggestionBuilder);
            searchSourceBuilder.suggest(suggestBuilder);
            final SearchRequest request = new SearchRequest();
            request.indices(index);
            request.source(searchSourceBuilder);
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            handler.handleResponse(response);
        } catch (IOException e) {
            log.warn("suggestion meet exception", e);
        }
    }
}
