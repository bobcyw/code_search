package com.caoyawen.code_search.model.gitlab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="projects")
public class ProjectModel {
    private Integer approvalsBeforeMerge;
    private Boolean archived;
    private String avatarUrl;
    private Boolean containerRegistryEnabled;
    private Date createdAt;
    private Integer creatorId;
    private String defaultBranch;
    private String description;
    private Integer forksCount;
    private String httpUrlToRepo;

    @Id
    private Integer id;
    private Boolean isPublic;
    private Boolean issuesEnabled;
    private Boolean jobsEnabled;
    private Date lastActivityAt;
    private Boolean lfsEnabled;
    private Boolean mergeRequestsEnabled;
    private String name;
    private String nameWithNamespace;
    private Boolean onlyAllowMergeIfPipelineSucceeds;
    private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;
    private Integer openIssuesCount;
    private String path;
    private String pathWithNamespace;
    private Boolean publicJobs;
    private String repositoryStorage;
    private Boolean requestAccessEnabled;
    private String runnersToken;
    private Boolean sharedRunnersEnabled;
    private Boolean snippetsEnabled;
    private String sshUrlToRepo;
    private Integer starCount;
    private Integer visibilityLevel;
    private Boolean wallEnabled;
    private String webUrl;
    private Boolean wikiEnabled;
    private Boolean printingMergeRequestLinkEnabled;
    private Boolean resolveOutdatedDiffDiscussions;
    private Boolean initializeWithReadme;
    private Boolean packagesEnabled;
    private Boolean emptyRepo;

    /*
    自定义属性
     */
    //group name
    private String nameSpaceName;
    //是否废弃
    private String abandon;
    //使用的分支
    private String branch;

    /*
    这些代码不能有
        private List<String> tagList;
        private MergeMethod mergeMethod;
        private ProjectStatistics statistics;
        private Visibility visibility;
        private List<ProjectSharedGroup> sharedWithGroups;
        private Permissions permissions;
        private Owner owner;
        private Namespace namespace;
   */

    /**
     * 从Gitlab的Project里进行clone
     * @param project gitlab api里的project模型
     */
    public void cloneFrom(Project project, String defaultBranch) {
        //自动clone
        BeanUtils.copyProperties(project, this);
        //手动处理一些field
        nameSpaceName = project.getNamespace().getName();
        if (project.getDefaultBranch() != null){
            branch = project.getDefaultBranch();
        }else{
            branch = defaultBranch;
        }
    }
}
