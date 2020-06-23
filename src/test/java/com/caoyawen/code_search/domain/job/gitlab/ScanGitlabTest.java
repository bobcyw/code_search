package com.caoyawen.code_search.domain.job.gitlab;

import com.caoyawen.code_search.properties.GitlabProperties;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Slf4j
@SpringBootTest
class ScanGitlabTest {
    ScanGitlab scanGitlab;

    @Autowired
    public void setScanGitlab(ScanGitlab scanGitlab) {
        this.scanGitlab = scanGitlab;
    }

    GitlabProperties gitlabProperties;

    @Autowired
    public void setGitlabProperties(GitlabProperties gitlabProperties) {
        this.gitlabProperties = gitlabProperties;
    }

    @Test
    void updateGroup() throws GitLabApiException {
        scanGitlab.updateProjects(gitlabProperties.getGroups());
    }

    @Test
    void writeLog() {
        final Date start = new Date();
        for (int i = 0; i < 10000; i++) {
            log.info("hello {}", i);
        }
        Date end = new Date();
        log.info("{} {}", start, end);
    }
}