package com.caoyawen.code_search.application.some_group;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FastjsonTest {
    Fastjson fastjson;

    @Autowired
    public void setFastjson(Fastjson fastjson) {
        this.fastjson = fastjson;
    }

    @Test
    void fastjsonWithUpdate() throws InterruptedException, TemplateException, IOException {
        fastjson.runWithUpdate().forEach(System.out::println);
    }
}