package com.caoyawen.code_search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * Gitlab相关的配置
 */
@Data
@ConfigurationProperties(prefix="gitlab")
public class GitlabProperties {
    @NotBlank
    private String token;

    @NotBlank
    private String apiUrl;

    @NotBlank
    private List<String> groups;

    @NotBlank
    private String storePath;

    @NotBlank
    private String defaultBranch;
}
