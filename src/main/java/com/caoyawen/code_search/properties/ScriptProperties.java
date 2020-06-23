package com.caoyawen.code_search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="script")
public class ScriptProperties {
    private String hello = "script/hello.sh";
    private String cloneProject = "script/clone_project.sh";
    private Integer cloneProjectRetry = 3;
    private String grepJinshan = "script/grep/jinshan.sh";
}
