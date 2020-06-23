package com.caoyawen.code_search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="code-search-job")
public class JobProperties {
    private Integer cloneProjectThreadPool = 4;
    private Integer grepProjectThreadPool = 6;
}
