package com.caoyawen.code_search.dataStruct;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class FIFOTest {
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    void testFIFO() throws OperatorException {
        assert (redisTemplate != null);
        final FIFO fifo = new FIFO();
        fifo.setKey("testFIFO");
        fifo.setMaxLength(5L);
        fifo.setZSetOp(redisTemplate.opsForZSet());
        fifo.add("a", 2L);
        fifo.add("a", 1L);
        fifo.add("b", 2L);
        fifo.add("c", 3L);
        fifo.add("d", 4L);
        fifo.add("f", 6L);
        fifo.add("e", 5L);
        List<String> all = fifo.getAll();
        assertEquals(all.size(), 5);
        assertArrayEquals(new String[]{"b", "c", "d", "e", "f"}, all.toArray(new String[]{}));
        fifo.remove("e");
        all = fifo.getAll();
        assertEquals(all.size(), 4);
        assertArrayEquals(new String[]{"b", "c", "d", "f"}, all.toArray(new String[]{}));
    }
}