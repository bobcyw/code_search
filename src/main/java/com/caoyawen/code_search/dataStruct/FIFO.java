package com.caoyawen.code_search.dataStruct;

import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class FIFO {
    private ZSetOperations<String, String> zSetOp;
    private Long maxLength;
    private String key;

    public List<String> getAll() throws OperatorException {
        final Long size = zSetOp.size(key);
        if(size != null){
            final Set<ZSetOperations.TypedTuple<String>> set = zSetOp.rangeWithScores(key, 0, size);
            if (set == null) {
                throw new OperatorException("getAll set is null");
            }
            List<String> ret = new LinkedList<>();
            for (ZSetOperations.TypedTuple<String> stringTypedTuple : set) {
                ret.add(stringTypedTuple.getValue());
            }
            return ret;
        }
        throw new OperatorException("getAll size is null");
    }

    public void add(String text, double value) throws OperatorException {
        final Boolean ret = zSetOp.add(key, text, value);
        if (ret == null) {
            throw new OperatorException("add ret is null");
        }
        if (!ret) return;
        Long size = zSetOp.size(key);
        if (size != null) {
            if(maxLength < size){
                zSetOp.removeRange(key, 0, size - maxLength-1);
            }
        }else{
            throw new OperatorException("add size is null");
        }
    }

    public void remove(String text){
        zSetOp.remove(key, text);
    }
}
