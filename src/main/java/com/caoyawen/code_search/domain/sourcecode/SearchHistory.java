package com.caoyawen.code_search.domain.sourcecode;

import com.caoyawen.code_search.dataStruct.FIFO;
import com.caoyawen.code_search.dataStruct.OperatorException;
import com.caoyawen.code_search.properties.SearchProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Data
@Slf4j
public class SearchHistory {
    SearchProperties searchProperties;
    RedisTemplate<String, String> redisTemplate;

    public SearchHistory(SearchProperties searchProperties, RedisTemplate<String, String> redisTemplate) {
        this.searchProperties = searchProperties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存历史记录
     * @param text 要保存的历史
     * @param key 用户id等标识
     */
    public void saveSearchText(String text, String key) {
        final FIFO fifo = getFifo(key);
        try {
            fifo.add(text, Instant.now().getEpochSecond());
        } catch (OperatorException e) {
            //直接把这个错误吞了，因为不是很重要
            log.warn("save search text error", e);
        }
    }

    private FIFO getFifo(String key) {
        final FIFO fifo = new FIFO();
        fifo.setZSetOp(redisTemplate.opsForZSet());
        fifo.setKey(searchProperties.getPrefix() +key);
        fifo.setMaxLength(searchProperties.getHistoryLimit());
        return fifo;
    }

    /**
     * 返回历史记录
     * @param key 用户id等标识
     * @return 返回历史记录结果
     */
    public List<String> getSearchText(String key){
        final FIFO fifo = getFifo(key);
        try {
            return fifo.getAll();
        } catch (OperatorException e) {
            //依然吞掉
            log.warn("get search text error", e);
            return Collections.singletonList("");
        }
    }
}
