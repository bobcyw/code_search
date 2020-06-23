package com.caoyawen.code_search.infrastructure.script;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 脚本的返回值
 */
@Data
public class Output {
    /**
     * 正常返回
     */
    List<String> stdOutput = new LinkedList<>();
    /**
     * 错误返回
     */
    List<String> errOutput = new LinkedList<>();
    /**
     * 脚本执行完的exit code
     */
    Integer existCode = -1;
    /**
     * 批量执行时需要的参数
     */
    String tag = "";

    Exception exception;

    public Output(String error, Exception exception) {
        errOutput.add(error);
        this.exception = exception;
    }

    public Output() {
    }
}
