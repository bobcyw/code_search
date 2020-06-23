package com.caoyawen.code_search.domain.job.table;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class GoogleTable {
    public List<FileDescription> parseFile(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            List<FileDescription> fileDescriptions = new LinkedList<>();
            Integer lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                try{
                    lineCount++;
                    final FileDescription fileDescription = new FileDescription(line);
                    fileDescriptions.add(fileDescription);
                }catch (ArrayIndexOutOfBoundsException e){
                    log.error("out of range [{}] {}", lineCount, line);
                    throw e;
                }
            }
            return fileDescriptions;
        }
    }

    public List<String> readLine(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            Set<String> lines = new HashSet<>();
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(trimSpaceInLine(line));
            }
            return new LinkedList<>(lines);
        }
    }

    enum Status {
        NORMAL,
        STATUS
    }

    String trimSpaceInLine(String line){
        final StringBuilder builder = new StringBuilder();
        Status status = Status.NORMAL;
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ' '){
                if(status == Status.STATUS){
                    continue;
                }
                status = Status.STATUS;
            }else{
                status = Status.NORMAL;
            }
            builder.append(line.charAt(i));
        }
        return builder.toString();
    }

    public List<FileDescription> combineFile(List<FileDescription> currents, List<String> scanResults) {
        Integer statusCap = currents.get(0).getStatus().size()+1;
        Map<String, FileDescription> currentM = new HashMap<>();
        for (FileDescription current : currents) {
            currentM.put(current.getFileContent(), current);
        }
        for (String scanResult : scanResults) {
            final FileDescription find = currentM.get(scanResult);
            if (find != null) {
                find.getStatus().add("exist");
            }else{
                currentM.put(scanResult, FileDescription.newByFile(scanResult, statusCap));
            }
        }
        List<FileDescription> ret = new LinkedList<>();
        currentM.forEach((k, v)->{
            if (v.getStatus().size() < statusCap) {
                v.getStatus().add("");
            }
            ret.add(v);
        });
        ret.sort(Comparator.comparing(FileDescription::getFileContent));
        final FileDescription header = ret.remove(ret.size()-1);
        ret.add(0, header);
        return ret;
    }
}
