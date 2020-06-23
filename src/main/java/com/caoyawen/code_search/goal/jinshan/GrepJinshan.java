package com.caoyawen.code_search.goal.jinshan;

import com.caoyawen.code_search.domain.job.CloneProjects;
import com.caoyawen.code_search.domain.job.GrepProjects;
import com.caoyawen.code_search.domain.job.gitlab.ScanGitlab;
import com.caoyawen.code_search.domain.job.table.FileDescription;
import com.caoyawen.code_search.domain.job.table.GoogleTable;
import com.caoyawen.code_search.properties.GitlabProperties;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一金山的搜索的所有功能
 */
@Component
public class GrepJinshan {
    //扫描gitlab
    ScanGitlab scanGitlab;

    @Autowired
    public void setScanGitlab(ScanGitlab scanGitlab) {
        this.scanGitlab = scanGitlab;
    }

    GitlabProperties gitlabProperties;

    @Autowired
    public void setGitlabProperties(GitlabProperties gitlabProperties) {
        this.gitlabProperties = gitlabProperties;
    }

    //克隆代码
    CloneProjects cloneProjects;

    @Autowired
    public void setCloneProjects(CloneProjects cloneProjects) {
        this.cloneProjects = cloneProjects;
    }

    //扫描代码
    GrepProjects grepProjects;

    @Autowired
    public void setGrepProjects(GrepProjects grepProjects) {
        this.grepProjects = grepProjects;
    }

    public GrepProjects getGrepProjects() {
        return grepProjects;
    }

    //合并报告
    GoogleTable googleTable = new GoogleTable();

    public static final String BACKUP = "项目废弃弃用废弃归属Flex服务已经不在用无用文件已废弃！！";

    /**
     * 将gitlab扫描,代码克隆，扫描，合并报告的功能合并在一起
     * @throws GitLabApiException
     * @throws InterruptedException
     * @throws IOException
     */
    public void allInOne() throws GitLabApiException, InterruptedException, IOException {
        //克隆项目
        cloneProjects();
        //扫描金山相关的代码
        grepProjects.grepJinshan();
        //合并报告
        mergeReport();
    }

    /**
     * 从gitlab拉取最新的元信息并克隆下来
     * @throws GitLabApiException
     * @throws InterruptedException
     */
    void cloneProjects() throws GitLabApiException, InterruptedException {
        scanGitlab.updateProjects(gitlabProperties.getGroups());
        cloneProjects.cloneProject();
    }

    void mergeReport() throws IOException {
        final List<FileDescription> fileDescriptions = googleTable.parseFile("/Users/caoyawen/data/jinshan/origin.txt");
        final List<String> news = googleTable.readLine("/Users/caoyawen/java/codescan/ksyun.com.txt");
        final List<FileDescription> rets = googleTable.combineFile(fileDescriptions, news);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/caoyawen/data/jinshan/ret.txt"))) {
            for (FileDescription ret : rets) {
                String status = String.join("\t", ret.getStatus());
                bufferedWriter.write(String.format("%s\t%s\t%s\t%s\t%s%n", ret.getFileContent(), ret.getHead(), ret.getComplete(), ret.getBackup(), status));
            }
        }

        //找出最新需要改进的项目单列
        final List<FileDescription> exists = rets.stream().filter((fileDescription -> {
            if (!fileDescription.getBackup().equals("") && BACKUP.contains(fileDescription.getBackup())) return false;
            return !fileDescription.getStatus().get(fileDescription.getStatus().size() - 1).equals("");
        })).collect(Collectors.toList());
        exists.add(0, rets.get(0));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/caoyawen/data/jinshan/exist.txt"))) {
            for (FileDescription exist : exists) {
                String status = String.join("\t", exist.getStatus());
                bufferedWriter.write(String.format("%s\t%s\t%s\t%s\t%s%n", exist.getFileContent(), exist.getHead(), exist.getComplete(), exist.getBackup(), status));
            }
        }
    }
}
