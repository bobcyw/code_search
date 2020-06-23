package com.caoyawen.code_search.script;

import com.caoyawen.code_search.model.gitlab.ProjectModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CloneProjectParameter {
    private String group;
    private String project;
    private String branch;
    private String gitUrl;

    public static CloneProjectParameter buildByProjectModel(ProjectModel project) {
        String gitUrl = project.getHttpUrlToRepo().substring(7);
        return CloneProjectParameter.builder().gitUrl(gitUrl).group(project.getNameSpaceName()).project(project.getName()).branch(project.getBranch()).build();
    }
}
