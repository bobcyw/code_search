package com.caoyawen.code_search.application.some_group.jinshan;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述金山Report每一行的数据
 */
@Data
@NoArgsConstructor
public class Line {
    private String fileContent;
    private String head;
    private String complete;
    private String backup;
    private List<String> status;

    public Line(String oneLine) {
        final String[] split = oneLine.split("\t", -1);
        fileContent = split[0];
        head = split[1];
        complete = split[2];
        backup = split[3];
        status = new LinkedList<>(Arrays.asList(Arrays.copyOfRange(split, 4, split.length)));
    }

    public static Line newByFile(String fileContent, Integer status) {
        final Line fileDescription = new Line();
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

    public String toString(){
        String status = String.join("\t", getStatus());
        return String.format("%s\t%s\t%s\t%s\t%s%n", getFileContent(), getHead(), getComplete(), getBackup(), status);
    }
}
