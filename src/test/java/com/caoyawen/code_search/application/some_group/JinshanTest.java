package com.caoyawen.code_search.application.some_group;

import com.caoyawen.code_search.application.some_group.jinshan.Jinshan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JinshanTest {
    Jinshan jinshan;

    @Autowired
    public void setJinshan(Jinshan jinshan) {
        this.jinshan = jinshan;
    }

    @Test
    void jinshanWithUpdate() throws Exception {
        jinshan.runWithUpdate();
    }
}