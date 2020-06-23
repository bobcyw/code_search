package com.caoyawen.code_search.application.some_group;

import com.caoyawen.code_search.domain.code.some_group.XincheManager;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 硬删除。
 * <p>现在新车里有一些项目对于数据表的操作是硬删除，这些要找出来加以修正。这个项目启动于2020-2，到目前还没有截止</p>
 */
@Component
public class HardDelete {
    XincheManager manager;

    @Autowired
    public void setManager(XincheManager manager) {
        this.manager = manager;
    }

    /**
     * 检索所有的代码，不包含更新代码库
     * @throws InterruptedException 触发Ctrl-C
     * @throws TemplateException 模版编写错误
     * @throws IOException I/O有问题
     */
    public List<String> run() throws InterruptedException, TemplateException, IOException {
        return manager.grep("[^/][^_<//=][Dd][Ee][Ll][Ee][Tt][Ee] [Ff][Rr][Oo][Mm]");
    }

    /**
     * 检索所有的代码，同时更新整个代码库
     * @throws InterruptedException 触发Ctrl-C
     * @throws TemplateException 模版编写错误
     * @throws IOException I/O有问题
     */
    public List<String> runWithUpdate() throws InterruptedException, TemplateException, IOException {
        manager.update();
        return run();
    }
}
