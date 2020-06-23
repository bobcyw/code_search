package com.caoyawen.code_search.domain.job;

import com.caoyawen.code_search.domain.job.gitlab.ScanGitlab;
import com.caoyawen.code_search.properties.GitlabProperties;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("jinshan")
class CloneProjectsTestForJinshan {
    CloneProjects cloneProjects;

    @Autowired
    public void setCloneProjects(CloneProjects cloneProjects) {
        this.cloneProjects = cloneProjects;
    }

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
    void cloneProjectsForJinshan() throws InterruptedException, GitLabApiException {
        scanGitlab.updateProjects(gitlabProperties.getGroups());
        cloneProjects.cloneProject();
    }
}