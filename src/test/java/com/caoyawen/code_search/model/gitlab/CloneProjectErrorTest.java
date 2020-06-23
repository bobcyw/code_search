package com.caoyawen.code_search.model.gitlab;

import com.caoyawen.code_search.utils.Time;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CloneProjectErrorTest {
    CloneProjectErrorRepo cloneProjectErrorRepo;

    @Autowired
    public void setCloneProjectErrorRepo(CloneProjectErrorRepo cloneProjectErrorRepo) {
        this.cloneProjectErrorRepo = cloneProjectErrorRepo;
    }

    @Test
    void createOne() {
        final CloneProjectError error = CloneProjectError.builder()
                .occurTime(Time.nowTimestamp()).output("output")
                .context("context")
                .projectId(12345).build();
        cloneProjectErrorRepo.save(error);
        final Optional<CloneProjectError> byId = cloneProjectErrorRepo.findById(1);
        assertTrue(byId.isPresent());
        byId.ifPresent(cloneProjectError -> log.info("{}", cloneProjectError.getOccurTime()));
    }
}