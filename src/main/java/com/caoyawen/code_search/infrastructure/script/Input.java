package com.caoyawen.code_search.infrastructure.script;

import lombok.Builder;
import lombok.Data;

/**
 * 单个script执行需要的信息
 */
@Builder
@Data
public class Input {
    /**
     * script 的内容
     */
    String content;
    /**
     * 对返回的内容进行标记，方便并发时匹配结果
     */
    String tag;
    /**
     * 执行script时候的环境变量
     */
    Env env;

    public Input(String content, String tag, Env env) {
        this.content = content;
        this.tag = tag;
        this.env = env;
    }

    public String getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }

    public Env getEnv() {
        return env;
    }

}
