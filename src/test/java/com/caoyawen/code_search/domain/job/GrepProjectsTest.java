package com.caoyawen.code_search.domain.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("jinshan")
class GrepProjectsTest {
    GrepProjects grepProjects;

    @Autowired
    public void setGrepProjects(GrepProjects grepProjects) {
        this.grepProjects = grepProjects;
    }

    @Test
    void testGrepProjects() throws InterruptedException {
        grepProjects.grepJinshan();
    }
}