package com.caoyawen.code_search.goal.jinshan;

import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@Slf4j
@SpringBootTest
@ActiveProfiles("jinshan")
class GrepJinshanTest {
    GrepJinshan grepJinshan;

    @Autowired
    public void setGrepJinshan(GrepJinshan grepJinshan) {
        this.grepJinshan = grepJinshan;
    }

    @Test
    void testAllInOne() throws InterruptedException, IOException, GitLabApiException {
        grepJinshan.allInOne();
    }

    @Test
    void scanProject() throws InterruptedException {
        grepJinshan.getGrepProjects().grepJinshan();
    }
}