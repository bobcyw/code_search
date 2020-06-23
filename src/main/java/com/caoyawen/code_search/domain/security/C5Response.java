package com.caoyawen.code_search.domain.security;

import lombok.Data;

@Data
public class C5Response {
    private Integer next;

    private Integer previous;

    private String count;

    private C5Result[] results;

    @Override
    public String toString()
    {
        return "ClassPojo [next = "+next+", previous = "+previous+", count = "+count+", results = "+results+"]";
    }
}
