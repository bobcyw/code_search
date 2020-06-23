package com.caoyawen.code_search.infrastructure.script;

import com.caoyawen.code_search.script.CloneProjectParameter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootTest
@ConfigurationPropertiesScan("com.caoyawen.code_search.properties")
@ContextConfiguration(classes = {Run.class})
@ActiveProfiles("default")
class RunTest implements ApplicationContextAware {
    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    Run run;

    @Autowired
    public void setRun(Run run) {
        this.run = run;
    }

    @Test
    void testHello() throws IOException {
        final Run runscript = (Run) applicationContext.getBean("run");
        final String s = runscript.runHello();
        log.info(s);
    }

    @Test
    void findResource() throws IOException {
        assert (RunTest.class.getClassLoader().getResource("script/hello.sh") != null);
    }

    @Test
    void cloneProject() throws IOException, InterruptedException {
        CloneProjectParameter cloneProjectParameter = CloneProjectParameter.builder().group("some-group").project("some-project")
                .branch("master").gitUrl("some-gitlab.domain/some-group/some-project.git").build();
        final String ret = run.cloneProject(cloneProjectParameter);
        log.info("{}", ret);
    }

    @Test
    void grepJinshan() throws IOException, InterruptedException {
        String[] givenProject = new String[]{
                "./some-group/some-project1",
                "./some-group/some-project2",
                "./some-group/some-project3"
        };
        for (String project : givenProject) {
            final String ret = run.grepJinshan(project);
            log.info("{}", ret);
        }
    }

    @Test
    void testNewHello() throws IOException, InterruptedException {
        final Run run = new Run();
        final Env env = new Env();
        env.setEnv("NAME", "caoyawen");
        final Output output = run.script("echo \"hello $NAME!\"", env);
        log.info("{}", output);
        assert(output.getStdOutput().get(0).equals("hello caoyawen!"));
        assert output.getErrOutput().size()==0;
    }

    @Test
    void testMultiHello() throws InterruptedException {
        final Inputs inputs = new Inputs();
        final Env env = new Env();
        env.setEnv("NAME", "caoyawen");
        final int count = 100;
        for (int i = 0; i < count; i++) {
            inputs.addScript(String.format("echo \"hello $NAME %d\"", i), env);
        }
        final Run run = new Run();
        final List<Output> outputs = run.multiScripts(inputs, -1);
        assert outputs.size() == 100;
        for (int i = 0; i < count; i++) {
            assert outputs.get(i).stdOutput.get(0).equals(String.format("hello caoyawen %d", i));
        }
    }
}