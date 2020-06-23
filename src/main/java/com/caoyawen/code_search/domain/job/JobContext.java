package com.caoyawen.code_search.domain.job;

import com.caoyawen.code_search.model.gitlab.ProjectModel;
import lombok.Data;

@Data
public class JobContext {
    ProjectModel projectModel;
    //elastic search需要指定的索引
    String index;
}
