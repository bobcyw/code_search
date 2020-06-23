package com.caoyawen.code_search.domain.job.table;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class FileDescriptionTest {
    @Test
    void parseLine() {
        String[] inputs=new String[]{
                "./growth/open/app/Services/ImageFormat.php: 'ksyun_origin' => 'https://some-domain.com/',\t李晓儒\t4月30日\tbackup\tcheck1\tcheck2\t",
                "./some-group/some-project/node_modules/blusher/widget/uploadImg/view/upload.js:// form.action = 'http://some-domain.com';\t李邦\t\t需要blusher修正\t\texist\texist",
                "./some-group/some-project/node_modules/blusher/widget/uploadImg/view/upload.js: url: 'http://some-domain.com',\t李邦\t\t需要blusher修正\t\texist\texist"
        };
        for (String input : inputs) {
            final FileDescription fileDescription = new FileDescription(input);
            log.info("[{}]{}", fileDescription.getStatus().size(), fileDescription);
            assertEquals(3, fileDescription.getStatus().size());
        }
    }
}