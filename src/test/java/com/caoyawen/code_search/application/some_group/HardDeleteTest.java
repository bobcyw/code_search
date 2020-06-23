package com.caoyawen.code_search.application.some_group;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HardDeleteTest {
    HardDelete hardDelete;

    @Autowired
    public void setHardDelete(HardDelete hardDelete) {
        this.hardDelete = hardDelete;
    }

    @Test
    void hardDelete() throws InterruptedException, TemplateException, IOException {
        hardDelete.runWithUpdate().forEach(System.out::println);
    }
}