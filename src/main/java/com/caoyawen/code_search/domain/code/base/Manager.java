package com.caoyawen.code_search.domain.code.base;

import com.caoyawen.code_search.infrastructure.git.project.Clone;
import com.caoyawen.code_search.infrastructure.git.project.Input;
import com.caoyawen.code_search.infrastructure.file.Grep;
import freemarker.template.TemplateException;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * 整个源代码管理的核心驱动类。
 * <p>
 *     目的是把代码从{@link Source}搬运到{@link Sink}，等配置好{@link Source}和{@link Sink}之后，执行驱动函数{@link #update()}就可以开始传输代码。
 * </p>
 * @see Input
 * @see Source
 * @see Sink
 */
@Data
public abstract class Manager {
    /**
     * 默认为系统<b>cpu数-1</b>的cpu数
     */
    private int threadPool = -1;
    private Source source;
    private Sink sink;
    private List<Input> inputs;

    public Manager(Source source, Sink sink) {
        this.source = source;
        this.sink = sink;
    }

    /**
     * 核心驱动函数，执行以后，就开始对项目进行更新。
     * @throws InterruptedException 遇到Ctrl-C
     * @throws TemplateException clone代码的script渲染遇到问题
     * @throws IOException 其他I/O问题
     */
    public void update() throws InterruptedException, TemplateException, IOException {
        Clone clone = new Clone();
        clone.multiProjects(getInputs(), threadPool);
    }

    /**
     * inputs是一个比较昂贵的对象，Lazy初始化完，就不再更新
     * @return 返回inputs
     */
    List<Input> getInputs(){
        if (inputs != null) {
            return inputs;
        }
        inputs = new LinkedList<>(source.getSourceURL(sink));
        return inputs;
    }

    /**
     * 并发遍历所有的项目目录，exclude、include等细节由子类来指定
     * @param grep 输入一个半初始化完成的grep，只有directories没有初始化，由本函数来执行
     * @return 返回扫描结果
     * @throws InterruptedException 遇到Ctrl-C
     * @throws TemplateException 渲染模版遇到问题
     * @throws IOException 其他I/O问题
     */
    public List<String> grep(Grep grep) throws InterruptedException, TemplateException, IOException {
        List<String> directories = new LinkedList<>();
        getInputs().forEach(i-> directories.add(Paths.get(i.getWorkDirectory(), i.getDestDirectory()).toString()));
        return grep.grepMultiDirectory(directories);
    }
}
