package com.caoyawen.code_search.domain.job;

import com.caoyawen.code_search.model.gitlab.ProjectRepo;
import com.caoyawen.code_search.properties.JobProperties;
import com.caoyawen.code_search.infrastructure.script.Run;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
public class GrepProjects {
    JobProperties jobProperties;

    @Autowired
    public void setJobProperties(JobProperties jobProperties) {
        this.jobProperties = jobProperties;
    }

    ProjectRepo projectRepo;

    @Autowired
    public void setProjectRepo(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    Run run;

    @Autowired
    public void setRun(Run run) {
        this.run = run;
    }

    public void grepJinshan() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(jobProperties.getGrepProjectThreadPool());
        List<Future<String>> scanResults = new LinkedList<>();
        projectRepo.findAll().forEach((projectModel -> {
            final Future<String> ret = executorService.submit(() -> {
                String directory = String.format("./%s/%s", projectModel.getNameSpaceName(), projectModel.getPath());
                log.info("scan {}", directory);
                try {
                    return run.grepJinshan(directory);
                } catch (IOException e) {
                    log.warn("IOException [{}]", directory,e);
                } catch (InterruptedException e) {
                    log.warn("InterruptedException [{}]", directory, e);
                }
                return "";
            });
            scanResults.add(ret);
        }));
        executorService.shutdown();
        log.info("shut down and wait for termination");
        executorService.awaitTermination(1, TimeUnit.DAYS);
        StringBuilder totalData = new StringBuilder();
        scanResults.forEach(result->{
            try {
                totalData.append(result.get());
            } catch (InterruptedException e) {
                log.warn("gather data InterruptedException", e);
            } catch (ExecutionException e) {
                log.warn("gather data ExecutionException", e);
            }
        });
        System.out.println(totalData.toString());
        //将输出写入到文件里
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/caoyawen/java/codescan/ksyun.com.txt"))) {
            bufferedWriter.write(totalData.toString());
        } catch (IOException e) {
            log.warn("write file error", e);
        }
    }
}
