package com.caoyawen.code_search.domain.code.some_group;

import com.caoyawen.code_search.domain.code.base.Manager;
import com.caoyawen.code_search.infrastructure.file.Grep;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 克隆新车项目的管理服务。
 * <p>
 *     配置好以后，通过函数update来驱动代码从Source到Sink的过程
 * </p>
 */
@Component
public class XincheManager extends Manager {

    @Autowired
    public XincheManager(GitlabSource source, LocalSink sink) {
        super(source, sink);
    }

    /**
     * 搜索特定的字符串。
     * @param matchString 特定匹配的字符串
     * @return 返回匹配的结果
     * @throws InterruptedException 触发Ctrl-C
     * @throws TemplateException 模版编写错误
     * @throws IOException I/O有问题
     */
    public List<String> grep(String matchString) throws InterruptedException, TemplateException, IOException {
        final Grep grep = Grep.builder().matchString(matchString)
                .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
                .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java", "pom.xml"))
                .build();
        return grep(grep);
    }
}
