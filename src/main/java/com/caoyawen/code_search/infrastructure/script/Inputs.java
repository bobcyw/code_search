package com.caoyawen.code_search.infrastructure.script;

import lombok.Data;

import java.util.*;

/**
 * 批量调用脚本的时候使用
 */
@Data
public class Inputs {
    List<Input> inputList = new LinkedList<>();
    Map<String, Input> contentMap = new HashMap<>();

    /**
     * 添加一个脚本，啥环境变量都没有
     * @param content 脚本内容
     */
    public void addScript(String content){
        addScript(content, new Env());
    }

    /**
     * 添加一个脚本
     * @param content 脚本内容
     * @param env 运行脚本的环境变量
     */
    public void addScript(String content, Env env){
        final String s = UUID.randomUUID().toString();
        addScript(content, s, env);
    }

    /**
     * 添加一个脚本，但还包括一个tag
     * @param content 脚本内容
     * @param tag tag是用于批量请求时，确保返回可以匹配上
     * @param env 运行脚本的环境变量
     */
    public void addScript(String content, String tag, Env env) {
        final Input c = new Input(content, tag, env);
        inputList.add(c);
        contentMap.put(tag, c);
    }

    /**
     * 通过输出匹配到原来对应的输入脚本
     * @param output 输出结果
     * @return 原来对应的脚本
     */
    public Input getContent(Output output) {
        return contentMap.get(output.getTag());
    }
}
