package com.caoyawen.code_search.domain.job.table;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class GoogleTableTest {
    GoogleTable gt = new GoogleTable();
    public static final String BACKUP = "项目废弃弃用废弃归属Flex服务已经不在用无用文件已废弃！！";

    @Test
    void testTrimLine() {
        String[] lines = new String[]{
                "./md-fe/m-public/src/utils/requestKss.js:        option.action = '//some-domain.com/';"
        };
        String[] rets = new String[]{
                "./md-fe/m-public/src/utils/requestKss.js: option.action = '//some-domain.com/';"
        };
        for (int i = 0; i < lines.length; i++) {
            assertEquals(rets[i], gt.trimSpaceInLine(lines[i]));
        }
    }

    @Test
    void readFile() throws IOException {
        final List<FileDescription> fileDescriptions = gt.parseFile("/Users/caoyawen/data/jinshan/origin.txt");
        final List<String> news = gt.readLine("/Users/caoyawen/java/codescan/ksyun.com.txt");
        final List<FileDescription> rets = gt.combineFile(fileDescriptions, news);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/caoyawen/data/jinshan/ret.txt"))) {
            for (FileDescription ret : rets) {
                String status = String.join("\t", ret.getStatus());
                bufferedWriter.write(String.format("%s\t%s\t%s\t%s\t%s\n", ret.getFileContent(), ret.getHead(), ret.getComplete(), ret.getBackup(), status));
            }
        }

        //找出最新需要改进的项目单列

        final List<FileDescription> exists = rets.stream().filter((fileDescription -> {
            if(!fileDescription.getBackup().equals("") && BACKUP.contains(fileDescription.getBackup())) return false;
            if(fileDescription.getStatus().get(fileDescription.getStatus().size()-1).equals("")){
                return false;
            }
            return true;
        })).collect(Collectors.toList());
        exists.add(0, rets.get(0));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/caoyawen/data/jinshan/exist.txt"))) {
            for (FileDescription exist : exists) {
                String status = String.join("\t", exist.getStatus());
                bufferedWriter.write(String.format("%s\t%s\t%s\t%s\t%s\n", exist.getFileContent(), exist.getHead(), exist.getComplete(), exist.getBackup(), status));
            }
        }
    }
}