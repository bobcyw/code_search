package com.caoyawen.code_search.domain.security;

import lombok.Data;

@Data
public class C5Result {
    private String inner_outner;

    private String dns_type;

    private String business_name;

    private String use_for;

    private String business;

    private String subbusiness;

    private String ip;

    private String subbusiness_name;

    private String env;

    private String path;

    private String oip;

    private String line_type;

    private String appid;

    private String name;

    private String id;

    private String person_duty;

    @Override
    public String toString()
    {
        return "ClassPojo [inner_outner = "+inner_outner+", dns_type = "+dns_type+", business_name = "+business_name+", use_for = "+use_for+", business = "+business+", subbusiness = "+subbusiness+", ip = "+ip+", subbusiness_name = "+subbusiness_name+", env = "+env+", path = "+path+", oip = "+oip+", line_type = "+line_type+", appid = "+appid+", name = "+name+", id = "+id+", person_duty = "+person_duty+"]";
    }
}
