package com.caoyawen.code_search.domain.sourcecode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchTableDetail {
    int no;
    String group;
    String project;
    String fileName;
}
