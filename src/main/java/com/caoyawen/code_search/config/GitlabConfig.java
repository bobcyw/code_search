package com.caoyawen.code_search.config;

import com.caoyawen.code_search.properties.GitlabProperties;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
public class GitlabConfig {
    /**
     * Scope为每次使用都分配一个
     * @param gitlabProperties 导入配置文件
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GitLabApi createGitLabApi(GitlabProperties gitlabProperties){
        return new GitLabApi(gitlabProperties.getApiUrl(), gitlabProperties.getToken());
    }
}
