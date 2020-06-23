package com.caoyawen.code_search.domain.code.some_group;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class GitlabSourceTest {
    GitlabSource gitlabSource;

    @Autowired
    public void setGitlabSource(GitlabSource gitlabSource) {
        this.gitlabSource = gitlabSource;
    }

    @Test
    void testURL() {
        log.info(gitlabSource.url2CloneURL("http://some-gitlab.domain/some-project/code_search.git"));
    }
}