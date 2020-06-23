package com.caoyawen.code_search.infrastructure.file;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrepTest {
    @Test
    void testTemplate() throws IOException, TemplateException {
        final Grep parameter = Grep.builder()
                .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
                .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java"))
                .matchString("ksyun.com")
                .directory("./some-group").build();
        assertEquals("#!/usr/bin/env bash\n" +
                "#grep -r --include=*.php --include=*.js --include=*.tsx --include=*.yml --include=*.java --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' \"$1\"\n" +
                "grep -r --include=\"*.php\" --include=\"*.js\" --include=\"*.tsx\" --include=\"*.yml\" --include=\"*.java\" --exclude=\"kss.ksyun.com.txt\" --exclude=\"ksyun.com.txt\" --exclude=\"ipday2_cn.ipdb\" --exclude=\"常用grep命令.txt\"  'ksyun.com' './some-group'\n", parameter.renderTemplate());
    }

    @Test
    void testNullExclude() throws IOException, TemplateException {
        final Grep parameter = Grep.builder()
                .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java"))
                .matchString("ksyun.com")
                .directory("./some-group").build();
        assertEquals("#!/usr/bin/env bash\n" +
                "#grep -r --include=*.php --include=*.js --include=*.tsx --include=*.yml --include=*.java --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' \"$1\"\n" +
                "grep -r --include=\"*.php\" --include=\"*.js\" --include=\"*.tsx\" --include=\"*.yml\" --include=\"*.java\"  'ksyun.com' './some-group'\n", parameter.renderTemplate());
    }

    @Test
    void testNullInclude() throws IOException, TemplateException {
        final Grep parameter = Grep.builder()
                .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
                .matchString("ksyun.com")
                .directory("./some-group").build();
        assertEquals("#!/usr/bin/env bash\n" +
                "#grep -r --include=*.php --include=*.js --include=*.tsx --include=*.yml --include=*.java --exclude=ksyun.com.txt --exclude=kss.ksyun.com.txt --exclude=ipday2_cn.ipdb --exclude=常用grep命令.txt 'ksyun.com' \"$1\"\n" +
                "grep -r --exclude=\"kss.ksyun.com.txt\" --exclude=\"ksyun.com.txt\" --exclude=\"ipday2_cn.ipdb\" --exclude=\"常用grep命令.txt\"  'ksyun.com' './some-group'\n", parameter.renderTemplate());
    }

    @Test
    void grep() throws InterruptedException, TemplateException, IOException {
        final Grep parameter = Grep.builder()
                .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
                .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java"))
                .matchString("ksyun.com")
                .directory("/Users/caoyawen/java/codescan/some-group/some-project").build();
        final List<String> results = parameter.grep();
        results.forEach(System.out::println);
    }

    @Test
    void grepMultiDirectory() throws InterruptedException, TemplateException, IOException {
        final Grep parameter = Grep.builder()
                .excludeFiles(Arrays.asList("kss.ksyun.com.txt", "ksyun.com.txt", "ipday2_cn.ipdb", "常用grep命令.txt"))
                .includeFiles(Arrays.asList("*.php", "*.js", "*.tsx", "*.yml", "*.java"))
                .matchString("ksyun.com").build();
        final List<String> results = parameter.grepMultiDirectory(Arrays.asList(
                "/Users/caoyawen/java/codescan/some-group/some-project1",
                "/Users/caoyawen/java/codescan/some-group/some-project2"
                ));
        results.forEach(System.out::println);
    }
}