package com.caoyawen.code_search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 代码搜索的配置文件
 * 可以加上如下的内容
 * codesearch.history_limit=10
 * codesearch.prefix=sh
 */
@Data
@ConfigurationProperties(prefix = "code-search")
public class SearchProperties {
    private Long historyLimit = 10L;
    private String prefix = "";
}
