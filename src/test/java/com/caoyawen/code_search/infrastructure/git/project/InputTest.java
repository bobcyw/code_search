package com.caoyawen.code_search.infrastructure.git.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {
    @Test
    void testRegex() {
        String[] given = new String[]{
                "git@some-gitlab.domain:some-project/code_search.git",
                "http://some-gitlab.domain/some-project/code_search.git"
        };
        String[] expect = new String[]{
                "code_search", "code_search"
        };
        for (int i = 0; i < given.length; i++) {
            final Input input = new Input(given[i], "some_directory", "master");
            assertEquals(expect[i], input.getDestDirectory());
        }
    }
}