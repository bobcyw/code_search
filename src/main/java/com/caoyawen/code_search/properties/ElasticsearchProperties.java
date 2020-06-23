package com.caoyawen.code_search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotBlank;

@Data
@ConfigurationProperties(prefix = "elastic-search")
public class ElasticsearchProperties {
    @NotBlank
    String hostname;

    @NotBlank
    Integer port;

    @NotBlank
    String scheme;

    @NotBlank
    String extension;
}
