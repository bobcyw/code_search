package com.caoyawen.code_search.domain.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class EnhanceTest {
    @Test
    void enhance() throws IOException, InterruptedException {
        final Enhance enhance = new Enhance();
        List<URLDescription> urls = new LinkedList<>();
        for (String url : enhance.readURL()) {
            urls.add(enhance.parseURL(url));
        }
        final ExecutorService pool = Executors.newFixedThreadPool(4);
        urls.forEach(s-> {
            pool.submit(s::enhance);
        });
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        urls.forEach(s->System.out.printf("%s\t%s\t%s\t%s%n", s.raw, s.domain, s.path, s.business));
    }
}