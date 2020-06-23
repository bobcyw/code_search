package com.caoyawen.code_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan({"com.caoyawen.code_search.properties", "com.caoyawen.code_search.domain"})
public class CodeSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSearchApplication.class, args);
    }

}
