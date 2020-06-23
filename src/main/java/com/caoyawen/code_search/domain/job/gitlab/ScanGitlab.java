package com.caoyawen.code_search.domain.job.gitlab;

import com.caoyawen.code_search.model.gitlab.ProjectModel;
import com.caoyawen.code_search.model.gitlab.ProjectRepo;
import com.caoyawen.code_search.properties.GitlabProperties;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * 从gitlab更新projects表
 */
@Slf4j
@Component
public class ScanGitlab {
    GitlabMetadata gitlabMetadata;

    @Autowired
    public void setGitlabMetadata(GitlabMetadata gitlabMetadata) {
        this.gitlabMetadata = gitlabMetadata;
    }

    ProjectRepo projectRepo;

    @Autowired
    public void setProjectRepo(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    GitlabProperties gitlabProperties;

    @Autowired
    public void setGitlabProperties(GitlabProperties gitlabProperties) {
        this.gitlabProperties = gitlabProperties;
    }

    public void updateProjects(List<String> groups) throws GitLabApiException {
        final List<Project> projects = gitlabMetadata.getProjects(groups);
        final List<ProjectModel> projectModels = new LinkedList<>();
        for (Project project : projects) {
            ProjectModel projectModel = new ProjectModel();
            projectModel.cloneFrom(project, gitlabProperties.getDefaultBranch());
            //保存
            projectModels.add(projectModel);

        }
        projectRepo.saveAll(projectModels);
    }
}
