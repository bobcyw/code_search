package com.caoyawen.code_search.domain.code.some_group;

import com.caoyawen.code_search.domain.code.base.Sink;
import com.caoyawen.code_search.domain.code.base.Source;
import com.caoyawen.code_search.infrastructure.git.project.Input;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * 新车代码源，来自gitlab。
 * <p>
 *     主要包含从gitlab提取代码
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "gitlab")
public class GitlabSource implements Source {
    @NotBlank
    private String token;

    @NotBlank
    private String apiUrl;

    @NotBlank
    private List<String> groups;

    @NotBlank
    private String defaultBranch;


    GitLabApi getGitlabApi(){
        return new GitLabApi(apiUrl, token);
    }

    @Override
    public List<Input> getSourceURL(Sink sink) {
        final GitLabApi api = getGitlabApi();
        List<Input> rets = new LinkedList<>();
        for (String groupName : groups) {
            try {
                final Group group = api.getGroupApi().getGroup(groupName);
                group.getProjects().forEach(project -> rets.add(buildInput(project, sink)));
            } catch (GitLabApiException e) {
                log.warn("getSourceURL meet gitlab exception", e);
            }
        }
        return rets;
    }

    /**
     * 通过gitlab api的Project和Sink sink来生成供{@link com.caoyawen.code_search.infrastructure.git.project.Clone}使用的{@link Input}。
     * <p>
     *     下面是gitlab api的一个Project内容常见截图
     * </p>
     * <pre>
     * approvalsBeforeMerge = null
     * archived = {Boolean@14574} false
     * avatarUrl = null
     * containerRegistryEnabled = {Boolean@14575} true
     * createdAt = {Date@14576} "Sat May 16 01:32:44 CST 2020"
     * creatorId = {Integer@14577} 678
     * defaultBranch = "master"
     * description = "代码搜索用"
     * forksCount = {Integer@14580} 0
     * forkedFromProject = null
     * httpUrlToRepo = "http://some-gitlab.domain/some-project/code_search.git"
     * id = {Integer@14582} 12700
     * isPublic = null
     * issuesEnabled = {Boolean@14575} true
     * jobsEnabled = {Boolean@14575} true
     * lastActivityAt = {Date@14583} "Mon Jun 08 18:48:08 CST 2020"
     * lfsEnabled = {Boolean@14575} true
     * mergeMethod = {Project$MergeMethod@14584} "merge"
     * mergeRequestsEnabled = {Boolean@14575} true
     * name = "code_search"
     * namespace = {Namespace@14586} "{\n  "id" : 2539,\n  "name" : "some-project",\n  "path" : "some-project",\n  "kind" : "group",\n  "fullPath" : "some-project"\n}"
     * nameWithNamespace = "some-project / code_search"
     * onlyAllowMergeIfPipelineSucceeds = {Boolean@14574} false
     * onlyAllowMergeIfAllDiscussionsAreResolved = {Boolean@14574} false
     * openIssuesCount = {Integer@14580} 0
     * owner = null
     * path = "code_search"
     * pathWithNamespace = "some-project/code_search"
     * permissions = null
     * publicJobs = {Boolean@14575} true
     * repositoryStorage = null
     * requestAccessEnabled = {Boolean@14574} false
     * runnersToken = null
     * sharedRunnersEnabled = {Boolean@14575} true
     * sharedWithGroups = {ArrayList@14590}  size = 0
     * snippetsEnabled = {Boolean@14575} true
     * sshUrlToRepo = "git@some-gitlab.domain:some-project/code_search.git"
     * starCount = {Integer@14580} 0
     * tagList = {ArrayList@14592}  size = 0
     * visibilityLevel = null
     * visibility = {Visibility@14593} "public"
     * wallEnabled = null
     * webUrl = "http://some-gitlab.domain/some-project/code_search"
     * wikiEnabled = {Boolean@14575} true
     * printingMergeRequestLinkEnabled = {Boolean@14575} true
     * resolveOutdatedDiffDiscussions = {Boolean@14574} false
     * statistics = null
     * initializeWithReadme = null
     * packagesEnabled = null
     * emptyRepo = null
     * </pre>
     * @param project gitlab家的Project模型，里面包含了上面这些内容
     * @param sink 需要存储的地方，通过组合{@link Sink#getSinkPath()}/groupName/projectName，来确定要存储的地方
     * @return 返回一个{@link Input}，来让git clone进行调用
     */
    Input buildInput(Project project, Sink sink){
        return new Input(url2CloneURL(project.getHttpUrlToRepo()),
                Paths.get(sink.getSinkPath(), project.getNamespace().getName()).toString(),
                project.getDefaultBranch() != null ? project.getDefaultBranch():defaultBranch,
                project.getPath()
                );
    }

    String url2CloneURL(String httpURL){
        return "http://gitlab-ci-token:" +
                token +
                "@" +
                httpURL.substring(7);
    }
}
