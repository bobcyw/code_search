package com.caoyawen.code_search.domain.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
public class URLDescription {
    String domain;
    String path;
    String business;
    String raw;

    void enhance(){
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<C5Response> response = restTemplate.getForEntity(String.format("http://c5.some-company.com/api/v2/domain/?format=json&name=%s", domain), C5Response.class);
        business = "未知部门";
        if (response.getBody() != null && response.getBody().getResults().length > 0) {
            business = response.getBody().getResults()[0].getBusiness();
        }
    }
}
