package com.caoyawen.code_search.infrastructure.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>模版渲染，核心概念</h2>
 * 整个渲染基于freemarker来进行，封装了freemark常见的配置参数，对外只暴露<b>模版(template)</b>，<b>上下文(context)</b>两个概念
 * <ul>
 *     <li><b>模版(template)</b> 每次渲染要指定可以用的模版，这个模版要放在classpath目录下</li>
 *     <li><b>上下文(context)</b> 渲染到模版里需要设置上下文k/v，上下文设置使用{@link #setContext(String, Object)}和{@link #setRoot(Map)}来实现</li>
 * </ul>
 * 设置完上下文，就可以开始渲染相关代码，调用函数{@link #render(String)}或者{@link #render()}
 * <p><b>例子:</b></p>
 * <pre>
 *     Render render = new Render();
 *     render.setContent("name", "caoyawen");
 *     //模版的内容为 ${name}
 *     System.out.println(render.render("templates/test/test1.ftl"));
 * </pre>
 * 得到 "caoyawen"
 */
@Slf4j
@Data
public class Render {
    static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
    static{
        try {
            cfg.setDirectoryForTemplateLoading(new File(Render.class.getClassLoader().getResource("").getPath()));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
        } catch (IOException e) {
            log.error("Initialization render mmet ioexception", e);
        }
    }

    Map<String, Object> root = new HashMap<>();
    String templateName;

    /**
     * 设置上下文参数
     * @param key 指定的key
     * @param val 指定的value
     */
    public void setContext(String key, Object val) {
        root.put(key, val);
    }

    /**
     * 给定模版的名称，进行渲染
     * @param templateName 模版的名称，其中，模版必须放在resources目录下，是一个相对路径，目前不提供绝对路径的解决方法
     * @return 渲染好的结果
     * @throws IOException 找不到文件等错误信息
     * @throws TemplateException 模版渲染错误
     * @see #render()
     */
    public String render(String templateName) throws IOException, TemplateException {
        setTemplateName(templateName);
        return render();
    }

    /**
     * 渲染模版，但模版的名称由{@link #setTemplateName(String)}来配置，方便只有少量参数修改，而模版文件不需要修改的场景
     * @return 返回渲染结果
     * @throws IOException 找不到文件等错误信息
     * @throws TemplateException 模版渲染错误
     * @see #render(String)
     */
    public String render() throws IOException, TemplateException {
        final Template template = cfg.getTemplate(templateName);
        final StringWriter writer = new StringWriter();
        template.process(root, writer);
        return writer.toString();
    }
}
