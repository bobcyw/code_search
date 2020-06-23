package com.caoyawen.code_search.domain.code.some_group;

import com.caoyawen.code_search.domain.code.base.Sink;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

/**
 * 本地存储
 */
@Data
@ConfigurationProperties(prefix = "gitlab")
public class LocalSink implements Sink {
    @NotBlank
    private String sinkPath;

    @Override
    public String getSinkPath() {
        return sinkPath;
    }
}
