package com.caoyawen.code_search.infrastructure.git.project;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用来记录克隆一个项目需要的各种参数。
 * <p>
 * 例如将 https://github.com/spring-projects/spring-boot.git 克隆到 ~/source 目录下
 * </p>
 * 则
 * <ul>
 *     <li><b>url</b> https://github.com/spring-projects/spring-boot.git</li>
 *     <li><b>destDirectory</b> spring-boot</li>
 *     <li><b>branch</b> 2.3.x</li>
 *     <li><b>workDirectory</b> ~/source</li>
 * </ul>
 */
@Data
public class Input {
    /**
     * clone 的URL域名
     */
    String url;
    /**
     * 将项目clone到指定目录
     */
    String destDirectory;
    /**
     * clone特定分支
     */
    String branch;
    /**
     * 整个clone工作所处的目录
     */
    String workDirectory;

    /**
     * @return 转换成映射，供模版渲染使用
     */
    public Map<String, Object> getMapper(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("url", url);
        map.put("branch", branch);
        map.put("destDirectory", destDirectory);
        return map;
    }

    private static final Pattern pattern = Pattern.compile(".*/(?<destDirectory>[A-Za-z_-]+).git$");

    /**
     * 构造函数。
     * <p>
     * 会自动推断 例如 git@some-gitlab.domain:some-project/code_search.git 或者 http://some-gitlab.domain/some-project/code_search.git 内的project，得到code_search,
     * 然后用 workDirectory/code_search，组合出真正的存储目录
     * @param url git的URL地址
     * @param workDirectory 存放克隆项目的工作目录
     * @param branch checkout的分支
     */
    public Input(String url, String workDirectory, String branch){
        this.url = url;
        this.workDirectory = workDirectory;
        this.branch = branch;
        final Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            this.destDirectory = matcher.group("destDirectory");
        }else{
            throw new IllegalArgumentException(String.format("%s not match regex", url));
        }
    }

    /**
     * 构造函数。
     * <p>
     *     所有参数都需要指定，通过这样的指定，项目将最终克隆到 workDirectory/destDirectory 指定的目录里
     * @param url git的URL地址
     * @param workDirectory 存放克隆项目的工作目录
     * @param branch checkout的分支
     * @param destDirectory 指定项目存放的子目录
     */
    public Input(String url, String workDirectory, String branch, String destDirectory) {
        this.url = url;
        this.destDirectory = destDirectory;
        this.branch = branch;
        this.workDirectory = workDirectory;
    }
}
