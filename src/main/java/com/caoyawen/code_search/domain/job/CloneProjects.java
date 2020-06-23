package com.caoyawen.code_search.domain.job;

import com.caoyawen.code_search.model.gitlab.CloneProjectError;
import com.caoyawen.code_search.model.gitlab.CloneProjectErrorRepo;
import com.caoyawen.code_search.model.gitlab.ProjectModel;
import com.caoyawen.code_search.model.gitlab.ProjectRepo;
import com.caoyawen.code_search.properties.JobProperties;
import com.caoyawen.code_search.script.CloneProjectParameter;
import com.caoyawen.code_search.infrastructure.script.Run;
import com.caoyawen.code_search.utils.Time;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 将项目clone到指定地方
 */
@Slf4j
@Component
public class CloneProjects {
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

    CloneProjectErrorRepo cloneProjectErrorRepo;

    @Autowired
    public void setCloneProjectErrorRepo(CloneProjectErrorRepo cloneProjectErrorRepo) {
        this.cloneProjectErrorRepo = cloneProjectErrorRepo;
    }

    JobProperties jobProperties;

    @Autowired
    public void setJobProperties(JobProperties jobProperties) {
        this.jobProperties = jobProperties;
    }

    public void cloneProject() throws InterruptedException {
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(jobProperties.getCloneProjectThreadPool());
        for (ProjectModel projectModel : projectRepo.findAll()) {
            executor.submit(()->{
                try {
                    log.info("cloning {}", projectModel.getNameWithNamespace());
                    run.cloneProject(CloneProjectParameter.buildByProjectModel(projectModel));
                } catch (IOException e) {
                    //发生了clone错误，记录下来接着做
                    final CloneProjectError error = CloneProjectError.builder()
                            .occurTime(Time.nowTimestamp()).output(e.toString())
                            .context(String.format("clone project %s", projectModel.getNameWithNamespace()))
                            .projectId(projectModel.getId()).build();
                    cloneProjectErrorRepo.save(error);
                    log.warn("{}", e.toString());
                } catch (InterruptedException e) {
                    //被强行退出了
                    //Todo 这里吞了？
                    log.warn("meet interrupt", e);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
