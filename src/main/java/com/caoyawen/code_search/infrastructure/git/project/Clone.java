package com.caoyawen.code_search.infrastructure.git.project;

import com.caoyawen.code_search.infrastructure.script.Env;
import com.caoyawen.code_search.infrastructure.script.Inputs;
import com.caoyawen.code_search.infrastructure.script.Output;
import com.caoyawen.code_search.infrastructure.script.Run;
import com.caoyawen.code_search.infrastructure.template.Render;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;

/**
 * 从git中克隆项目
 */
public class Clone {
    private static final String TEMPLATE_NAME = "script/clone/clone_project.ftl";

    /**
     * @param inputs 批量克隆项目的参数
     * @param threadPool 见 com.caoyawen.code_search.infrastructure.script.Run里关于multiScripts的threadPool的解释
     * @return Run的输出结果
     * @throws InterruptedException 进程被中断
     * @throws IOException 有IO错误
     * @throws TemplateException 模版有错误
     */
    public List<Output> multiProjects(List<Input> inputs, int threadPool) throws InterruptedException, IOException, TemplateException {
        final Inputs scriptInputs = new Inputs();
        for (Input i : inputs) {
            final Render render = new Render();
            render.setRoot(i.getMapper());
            final Env env = new Env();
            env.setWorkDirectory(i.getWorkDirectory());
            scriptInputs.addScript(render.render(TEMPLATE_NAME), env);
        }
        final Run run = new Run();
        return run.multiScripts(scriptInputs, threadPool);
    }

    /**
     * @param input 输入参数
     * @return 输出结果
     * @throws IOException 进程被中断
     * @throws TemplateException 有IO错误
     * @throws InterruptedException 模版有错误
     */
    public Output project(Input input) throws IOException, TemplateException, InterruptedException {
        final Render render = new Render();
        render.setRoot(input.getMapper());

        final Run run = new Run();
        final Env env = new Env();
        env.setWorkDirectory(input.getWorkDirectory());
        return run.script(render.render(TEMPLATE_NAME), env);
    }
}
