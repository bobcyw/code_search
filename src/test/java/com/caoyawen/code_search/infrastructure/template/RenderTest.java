package com.caoyawen.code_search.infrastructure.template;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RenderTest {
    @Test
    void normalTest() throws IOException, TemplateException {
        Render render = new Render();
        render.setContext("name", "caoyawen");
        render.setTemplateName("templates/test/test1.ftl");
        assertEquals("caoyawen", render.render());
    }

    @Test
    void normalTest2() throws IOException, TemplateException {
        Render render = new Render();
        render.setContext("name", "caoyawen");
        assertEquals("caoyawen", render.render("templates/test/test1.ftl"));
    }
}