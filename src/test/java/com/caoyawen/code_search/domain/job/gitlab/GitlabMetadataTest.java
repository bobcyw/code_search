package com.caoyawen.code_search.domain.job.gitlab;

import com.caoyawen.code_search.config.GitlabConfig;
import com.caoyawen.code_search.properties.GitlabProperties;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ContextConfiguration(classes={GitlabMetadata.class, GitlabConfig.class})
@ConfigurationPropertiesScan("com.caoyawen.code_search.properties")
class GitlabMetadataTest {
    GitlabMetadata gitlabMetadata;

    @Autowired
    public void setGitlabMetadata(GitlabMetadata gitlabMetadata) {
        this.gitlabMetadata = gitlabMetadata;
    }

    GitlabProperties properties;

    @Autowired
    public void setProperties(GitlabProperties properties) {
        this.properties = properties;
    }

    @Test
    void getProjects() throws GitLabApiException {
        final List<Project> projects = gitlabMetadata.getProjects(properties.getGroups());
        for (Project project : projects) {
            log.info("{}", project);
        }
    }

    @Test
    void getProject() throws GitLabApiException {
        final Project project = gitlabMetadata.getProject("some-project", "code_search");
        assertEquals("code_search", project.getName());
        assertEquals("some-project", project.getNamespace().getName());
        log.info("{}", project);
    }
}