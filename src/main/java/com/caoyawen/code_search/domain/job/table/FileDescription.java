package com.caoyawen.code_search.domain.job.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述目前项目的现状
 */
@Data
@NoArgsConstructor
public class FileDescription {
    private String fileContent;
    private String head;
    private String complete;
    private String backup;
    private List<String> status;

    public FileDescription(String oneLine) {
        final String[] split = oneLine.split("\t", -1);
        fileContent = split[0];
        head = split[1];
        complete = split[2];
        backup = split[3];
        status = new LinkedList<>(Arrays.asList(Arrays.copyOfRange(split, 4, split.length)));
    }

    public static FileDescription newByFile(String fileContent, Integer status) {
        final FileDescription fileDescription = new FileDescription();
        fileDescription.fileContent = fileContent;
        fileDescription.head = "";
        fileDescription.complete = "";
        fileDescription.backup = "";
        fileDescription.status = new LinkedList<>();
        for (Integer i = 0; i < status-1; i++) {
            fileDescription.status.add("");
        }
        fileDescription.status.add("new!!");
        return fileDescription;
    }
}
