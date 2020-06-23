package com.caoyawen.code_search.application.some_group.jinshan;

import com.caoyawen.code_search.domain.code.some_group.XincheManager;
import com.caoyawen.code_search.infrastructure.file.FileLoop;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 金山云迁移核心类
 */
@Component
public class Jinshan {
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
    public void run() throws IOException, TemplateException, InterruptedException {
        final List<String> latestResult = manager.grep("ksyun.com");
        try(final BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/caoyawen/data/jinshan/ret.txt"))) {
            final Report report = new Report(writer);
            final FileLoop reader = new FileLoop("/Users/caoyawen/data/jinshan/origin.txt");
            report.readOldReport(reader);
            report.mergeReport(latestResult);
        }
    }

    /**
     * 检索所有的代码，同时更新整个代码库
     * @throws InterruptedException 触发Ctrl-C
     * @throws TemplateException 模版编写错误
     * @throws IOException I/O有问题
     */
    public void runWithUpdate() throws InterruptedException, TemplateException, IOException {
        manager.update();
        run();
    }
}
