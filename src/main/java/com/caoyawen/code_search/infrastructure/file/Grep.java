package com.caoyawen.code_search.infrastructure.file;

import com.caoyawen.code_search.infrastructure.script.Inputs;
import com.caoyawen.code_search.infrastructure.script.Output;
import com.caoyawen.code_search.infrastructure.script.Run;
import com.caoyawen.code_search.infrastructure.template.Render;
import freemarker.template.TemplateException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 实现扫描文件的功能
 * <p>
 * 考虑到多核CPU和SSD硬盘的利用率，有单目录扫描 {@link #grep()} 和多目录并发扫描 {@link #grepMultiDirectory(List)}两种情况。
 * </p>
 * <p>多目录并发扫描时，会使用比当前CPU核数少1的所有其他内核，确保最大吞吐量。其核心概念包括，目录(directory)，待扫描的特征串(matchString)，待包含的文件(includeFile)，以及待排除的文件(excludeFile)，以及结果(result)</p>
 * 单目录扫描例子代码如下
 * <pre>
 *        final Grep parameter = Grep.builder()
 *                 .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
 *                 .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java"))
 *                 .matchString("ksyun.com")
 *                 .directory("/Users/caoyawen/java/codescan/some-group/some-project").build();
 *         final List<String> results = parameter.grep();
 *         results.forEach(System.out::println);
 * </pre>
 * 多目录并发扫描例子代码如下
 * <pre>
 *         final Grep parameter = Grep.builder()
 *                 .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
 *                 .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java"))
 *                 .matchString("ksyun.com").build();
 *         final List<String> results = parameter.grepMultiDirectory(Arrays.asList(
 *                 "/Users/caoyawen/java/codescan/some-group/some-project1",
 *                 "/Users/caoyawen/java/codescan/some-group/some-project2"
 *                 ));
 *         results.forEach(System.out::println);
 * </pre>
 */
@Data
@Builder
@Slf4j
public class Grep {
    /**
     * 待扫描目录，注意目录要是<b>绝对路径</b>
     */
    String directory;
    /**
     * 需要排除的文件
     */
    List<String> excludeFiles;
    /**
     * 需要包含的文件
     */
    List<String> includeFiles;
    /**
     * 需要查找的特征串
     */
    String matchString;

    /**
     * 模版文件
     */
    static final String TEMPLATE_NAME = "script/grep/grep.ftl";

    /**
     * 渲染一个模版
     * @return 返回一个已经渲染好的grep脚本，可以供{@link com.caoyawen.code_search.infrastructure.script.Run} 进行调用
     * @throws IOException 找不到模版文件时进行报错
     * @throws TemplateException 模版渲染有问题进行报错
     */
    String renderTemplate() throws IOException, TemplateException {
        Render render = new Render();
        render.setContext("includes", includeFiles);
        render.setContext("excludes", excludeFiles);
        render.setContext("match", matchString);
        render.setContext("directory", directory);
        return render.render(TEMPLATE_NAME);
    }

    /**
     * 其他参数相同，批量渲染多个扫描目录，方便由{@link com.caoyawen.code_search.infrastructure.script.Run#multiScripts(Inputs, int)}并发调用
     * @param directories 多个目录
     * @return 返回多个目录组成的多个扫描脚本
     * @throws IOException 找不到模版文件时进行报错
     * @throws TemplateException 模版渲染有问题进行报错
     */
    List<String> renderTemplateWithMultiDirectory(List<String> directories) throws IOException, TemplateException {
        List<String> rets = new LinkedList<>();
        Render render = new Render();
        render.setContext("includes", includeFiles);
        render.setContext("excludes", excludeFiles);
        render.setContext("match", matchString);
        render.setTemplateName(TEMPLATE_NAME);
        for (String d : directories) {
            render.setContext("directory", d);
            rets.add(render.render());
        }
        return rets;
    }

    /**
     * 扫描单个目录
     * @return 返回扫描的结果
     * @throws IOException 找不到模版文件报错
     * @throws TemplateException 渲染模版出问题报错
     * @throws InterruptedException 发生强行中断
     */
    public List<String> grep() throws IOException, TemplateException, InterruptedException {
        Run run = new Run();
        final Output output = run.script(renderTemplate());
        return output.getStdOutput();
    }

    /**
     * 扫描多个目录
     * @param directories 给定的目录，注意要是绝对路径
     * @return 返回扫描的结果
     * @throws IOException 找不到模版文件报错
     * @throws TemplateException 渲染模版出问题报错
     * @throws InterruptedException 发生强行中断
     */
    public List<String> grepMultiDirectory(List<String> directories) throws IOException, TemplateException, InterruptedException {
        Run run = new Run();
        final List<String> scripts = renderTemplateWithMultiDirectory(directories);
        final Inputs inputs = new Inputs();
        scripts.forEach(inputs::addScript);
        final List<Output> outputs = run.multiScripts(inputs, -1);
        List<String> results = new LinkedList<>();
        outputs.forEach(o-> results.addAll(o.getStdOutput()));
        return results;
    }
}
