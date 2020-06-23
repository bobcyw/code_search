package com.caoyawen.code_search.domain.job.gitlab;

import lombok.Data;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * 得到Gitlab的所有project
 */
@Data
@Component
public class GitlabMetadata {
    private GitLabApi api;

    @Autowired
    public void setApi(GitLabApi api) {
        this.api = api;
    }

    public List<Project>getProjects(List<String> groups) throws GitLabApiException {
        List<Project> projects = new LinkedList<>();
        for (String groupName : groups) {
            final Group group = api.getGroupApi().getGroup(groupName);
            projects.addAll(group.getProjects());
        }
        return projects;
    }

    public Project getProject(String namespace, String project) throws GitLabApiException {
        return api.getProjectApi().getProject(namespace, project);
    }
}
