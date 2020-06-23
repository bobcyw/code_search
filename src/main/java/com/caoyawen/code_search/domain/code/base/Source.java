package com.caoyawen.code_search.domain.code.base;

import com.caoyawen.code_search.infrastructure.git.project.Input;

import java.util.List;

/**
 * 定义代码的来源，返回一个可以同克隆的git地址，和需要使用branch
 */
public interface Source {
    List<Input> getSourceURL(Sink sink);
}
