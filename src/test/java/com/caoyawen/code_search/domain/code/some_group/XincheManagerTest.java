package com.caoyawen.code_search.domain.code.some_group;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class XincheManagerTest {
    XincheManager manager;

    @Autowired
    public void setManager(XincheManager manager) {
        this.manager = manager;
    }

    @Test
    void cloneXinche() throws InterruptedException, TemplateException, IOException {
        manager.update();
    }

    @Test
    void grepXincheJinshanyun() throws InterruptedException, TemplateException, IOException {
        final List<String> result = manager.grep("ksyun.com");
        result.forEach(System.out::println);
    }

    @Test
    void grepFastJSON() throws InterruptedException, TemplateException, IOException {
        final List<String> result = manager.grep("<artifactId>fastjson</artifactId>");
        result.forEach(System.out::println);
    }
}