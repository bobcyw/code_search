package com.caoyawen.code_search.infrastructure.git.project;

import com.caoyawen.code_search.infrastructure.script.Output;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CloneTest {
    static ConcurrentLinkedQueue<String> deleteDirectory = new ConcurrentLinkedQueue<>();

    @AfterAll
    static void clean(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            deleteDirectory.forEach(d->{
                FileUtils.deleteQuietly(new File(d));
            });
        }));
    }

    @Test
    void cloneOneProject() throws InterruptedException, TemplateException, IOException {
        final Clone clone = new Clone();
        final Path path = Files.createTempDirectory("code_search");
        deleteDirectory.add(path.toString());
        final Input input = new Input("http://some-gitlab.domain/some-project/code_search.git", path.toString(), "master", "code_search");
        final Output output = clone.project(input);
        assertEquals(0, output.getExistCode());
    }

    @Test
    void cloneOneProject2() throws InterruptedException, TemplateException, IOException {
        final Clone clone = new Clone();
        final Path path = Files.createTempDirectory("code_search");
        deleteDirectory.add(path.toString());
        final Input input = new Input("http://some-gitlab.domain/some-project/code_search.git", path.toString(), "master");
        final Output output = clone.project(input);
        assertEquals(0, output.getExistCode());
    }

    @Test
    void cloneMultiProject() throws InterruptedException, TemplateException, IOException {
        List<Input> inputs = new LinkedList<>();
        final Clone clone = new Clone();
        for (int i = 0; i < 10; i++) {
            final Path path = Files.createTempDirectory("code_search");
            deleteDirectory.add(path.toString());
            final Input input = new Input("http://some-gitlab.domain/some-project/code_search.git", path.toString(), "master", "code_search");
            inputs.add(input);
        }
        final List<Output> outputs = clone.multiProjects(inputs, -1);
        outputs.forEach(o->{
            assertEquals(0, o.getExistCode());
        });
    }
}