package com.caoyawen.code_search.infrastructure.script;

import com.caoyawen.code_search.properties.GitlabProperties;
import com.caoyawen.code_search.properties.ScriptProperties;
import com.caoyawen.code_search.script.CloneProjectParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Run 是script的调度核心类，所有的调度代码都在这里
 */
@Component
@Slf4j
public class Run {
    /**
     * 公共克隆项目，内含一个从0次开始重试
     * @param cloneProjectParameter 克隆项目相关的参数
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String cloneProject(CloneProjectParameter cloneProjectParameter) throws IOException, InterruptedException {
        return cloneProject(cloneProjectParameter, 0);
    }

    /**
     * 克隆项目
     * @param cloneProjectParameter 克隆相关的参数
     * @param retry 重试的次数，超过就放弃重试了
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private String cloneProject(CloneProjectParameter cloneProjectParameter, Integer retry) throws IOException, InterruptedException {
        final URL url = Run.class.getClassLoader().getResource(scriptProperties.getCloneProject());
        if (url == null) {
            throw new FileNotFoundException(scriptProperties.getCloneProject());
        }
        final ProcessBuilder processBuilder = new ProcessBuilder("sh", url.getPath(),
                cloneProjectParameter.getGitUrl(), cloneProjectParameter.getBranch(),
                cloneProjectParameter.getProject(), cloneProjectParameter.getGroup());
        //设置环境变量
        final Map<String, String> env = processBuilder.environment();
        env.put("GITLAB_ACCESS_TOKEN", gitlabProperties.getToken());
        //设置当前工作目录
        processBuilder.directory(new File(gitlabProperties.getStorePath()));
        //合并错误和正确输出，都到正确出里
        processBuilder.redirectErrorStream(true);

        final Process process = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final String ret = readBuffer(bufferedReader);
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                if (ret.contains("已经存在，并且不是一个空目录") && retry + 1 <= scriptProperties.getCloneProjectRetry()) {
                    //删除这个目录再试一次，但只能做配置里指定的retry次数
                    final Path path = Paths.get(gitlabProperties.getStorePath(), cloneProjectParameter.getGroup(), cloneProjectParameter.getProject());
                    FileUtils.deleteDirectory(new File(path.toString()));
                    log.info("delete {} and retry", path.toString());
                    cloneProject(cloneProjectParameter, retry + 1);
                }else{
                    //普通情况
                    throw new IOException(String.format("ret is not 0 [%d] clone %s to %s %n %s", exitCode, cloneProjectParameter.getGitUrl(), gitlabProperties.getStorePath(), ret));
                }
            }
        } catch (InterruptedException e) {
            //强行中断了
            log.warn("meet interrupt exception", e);
            log.warn("{}\n", ret);
            throw e;
        }
        return ret;
    }
    ScriptProperties scriptProperties;

    @Autowired
    public void setScriptProperties(ScriptProperties scriptProperties) {
        this.scriptProperties = scriptProperties;
    }

    GitlabProperties gitlabProperties;

    static final String sep = System.getProperty("line.separator");

    @Autowired
    public void setGitlabProperties(GitlabProperties gitlabProperties) {
        this.gitlabProperties = gitlabProperties;
    }

    public String runHello() throws IOException {
        assert (scriptProperties != null);
        assert (scriptProperties.getHello() != null);
        final URL url = Run.class.getClassLoader().getResource(scriptProperties.getHello());
        if (url == null) {
            throw new FileNotFoundException(scriptProperties.getHello());
        }
        final ProcessBuilder source = new ProcessBuilder("sh",url.getPath(), "caoyawen");
        final Process process = source.start();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return readBuffer(reader);
    }

    String readBuffer(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null){
            builder.append(line);
            builder.append(sep);
        }
        return builder.toString();
    }

    List<String> readBufferForList(BufferedReader reader) throws IOException {
        List<String> rets = new LinkedList<>();
        String line;
        while((line = reader.readLine()) != null){
            rets.add(line);
        }
        return rets;
    }

    /**
     * 过滤金山的项目
     */
    public String grepJinshan(String scanDirectory) throws IOException, InterruptedException {
        //得到script
        final URL url = Run.class.getClassLoader().getResource(scriptProperties.getGrepJinshan());
        if (url == null) {
            throw new FileNotFoundException(scriptProperties.getGrepJinshan());
        }
        final ProcessBuilder processBuilder = new ProcessBuilder("sh", url.getPath(), scanDirectory);
//        processBuilder.redirectErrorStream(true);
        //设定工作目录为代码存储目录
        processBuilder.directory(new File(gitlabProperties.getStorePath()));
        final Process process = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final BufferedReader errBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        final String ret = readBuffer(bufferedReader);
        final String errRet = readBuffer(errBuffer);
        try {
            final int exitCode = process.waitFor();
            if (exitCode != 1 && exitCode != 0 && !errRet.contains("No such file or directory")) {
                throw new IOException(String.format("ret is not 0, it's %d%n%s", exitCode, errRet));
            }
        } catch (InterruptedException | IOException e) {
            //发生了强行中断
            log.warn("meet interrupt exception", e);
            log.warn("{}", ret);
            throw e;
        }
        return ret;
    }

    /**
     * 执行单个脚本，啥参数都没有
     * @param content 脚本的内容
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Output script(String content) throws IOException, InterruptedException {
        return script(content, new Env());
    }

    /**
     * 执行单个脚本
     * @param content 脚本内容
     * @param env 脚本的环境变量
     * @return 脚本的返回
     * @throws IOException
     * @throws InterruptedException
     */
    public Output script(String content, Env env) throws IOException, InterruptedException {
        return script(Input.builder().content(content).env(env).tag("").build());
    }

    /**
     * 执行单个脚本
     * @param input 需要执行的脚本内容
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private Output script(Input input) throws IOException, InterruptedException {
        String shellContent = input.getContent();
        Env env = input.getEnv();
        String tag = input.getTag();
        final File tempFile = File.createTempFile("code_search_", "run_shell");
        final Output output = new Output();
        tempFile.deleteOnExit();
        try (FileWriter fileWriter = new FileWriter(tempFile.getAbsolutePath(), true)) {
            fileWriter.write(shellContent);
        }
        final ProcessBuilder processBuilder = new ProcessBuilder("sh", tempFile.getAbsolutePath());
        //设置工作目录
        if (env.getWorkDirectory() != null) {
            processBuilder.directory(new File(env.getWorkDirectory()));
        }else{
            //如果不存在，就用脚本的临时目录
            processBuilder.directory(new File(tempFile.getParent()));
        }
        //设置环境变量
        if (env.getEnvironment() != null) {
            final Map<String, String> environment = processBuilder.environment();
            env.getEnvironment().forEach(environment::put);
        }
        //设置输出
        processBuilder.redirectErrorStream(env.getCombineOutput());
        //启动
        final Process process = processBuilder.start();
        final BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final BufferedReader errorBuffer = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        try {
            output.setExistCode(process.waitFor());
            output.setStdOutput(readBufferForList(inputBuffer));
            output.setErrOutput(readBufferForList(errorBuffer));
        } catch (InterruptedException e) {
            log.warn("run shell meet interrupt exception", e);
            output.setException(e);
            throw e;
        }

        output.setTag(tag);
        return output;
    }

    /**
     * 批量执行脚本，每个脚本有一个对应的返回
     * @param inputs 批量执行的脚本参数
     * @param threadPool {@literal >} 0 : 指定线程数
     *                   {@literal <}= 0 : 当前cpu核数 + threadPool。结果最小不低于1
     * @return 返回批量结果
     */
    public List<Output> multiScripts(Inputs inputs, int threadPool) throws InterruptedException {
        threadPool = Math.max(1, threadPool < 1 ? Runtime.getRuntime().availableProcessors() + threadPool : threadPool);
        final ExecutorService pool = Executors.newFixedThreadPool(threadPool);
        List<Future<Output>> submits = inputs.getInputList().stream().map(c -> pool.submit(() -> {
            return script(c);
        })).collect(Collectors.toCollection(LinkedList::new));
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        List<Output> rets = new LinkedList<>();
        for (Future<Output> s : submits) {
            try {
                rets.add(s.get());
            } catch (InterruptedException e) {
                rets.add(new Output("interrupt", e));
                throw e;
            } catch (ExecutionException e) {
                rets.add(new Output("execution exception", e));
            }
        }
        return rets;
    }
}
