package com.caoyawen.code_search.domain.security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Enhance {
    public List<String> readURL() throws IOException {
        List<String> rets = new LinkedList<>();;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/caoyawen/data/security/raw-domain.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rets.add(line);
            }
        }
        return rets;
    }

    URLDescription parseURL(String url) throws MalformedURLException {
        final URL r = new URL(url);
        /*
            protocol = "http"
            host = "some-domain.com"
            port = -1
            file = "/actuator"
            query = null
            authority = "some-domain.com"
            path = "/actuator"
            userInfo = null
            ref = null
            hostAddress = null
            handler = {Handler@13640}
            hashCode = -1
            tempState = null
         */
        return URLDescription.builder().domain(r.getHost()).path(r.getPath()).raw(url).build();
    }
}
