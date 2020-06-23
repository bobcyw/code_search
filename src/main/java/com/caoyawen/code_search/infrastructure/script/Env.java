package com.caoyawen.code_search.infrastructure.script;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义脚本执行的环境变量
 */
@Data
public class Env {
    /**
     * 指定脚本执行的工作目录
     */
    String workDirectory;
    /**
     * 定义需要添加的环境变量
     */
    Map<String, String> environment = new HashMap<>();
    /**
     * 让输入输出合并成一个结果，如果为true，则所有输出结果都放在Output.stdOutput里面
     */
    Boolean combineOutput = false;

    /**
     * 一种特别的ENV，只是简单告诉项目将所有grep结果放在一起
     */
    public static final Env COMBINE_ENV = new Env();
    static{
        COMBINE_ENV.combineOutput = true;
    }
    /**
     * 设置环境变量，k/v结构，都是字符串，常用于加密内容，动态内容等
     * @param key 输入的key
     * @param val key代表的变量
     */
    public void setEnv(String key, String val){
        environment.put(key, val);
    }
}
