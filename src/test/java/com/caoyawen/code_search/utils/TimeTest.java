package com.caoyawen.code_search.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class TimeTest {
    @Test
    void now() {
        final Time time = new Time();
        log.info("{}", time.now());
    }

    @Test
    void nowTimestamp() {
        log.info("{}", Time.nowTimestamp());
    }
}