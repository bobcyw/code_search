package com.caoyawen.code_search.properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ConfigurationPropertiesScan("com.caoyawen.code_search.properties")
class SearchPropertiesTest {
    private SearchProperties sp;

    @Autowired
    public void setSh(SearchProperties sp) {
        this.sp = sp;
    }

    @Test
    void testProperties() {
        assert (sp != null);
        assertEquals("sh", sp.getPrefix());
        assertEquals(11L, sp.getHistoryLimit());
    }
}