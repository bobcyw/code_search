package com.caoyawen.code_search.infrastructure.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * 读取csv文件的内容
 */
public class FileLoop implements Loop {
    public FileLoop(String fileName) {
        this.fileName = fileName;
    }

    public FileLoop() {
    }

    /**
     * 指定文件的名称
     */
    String fileName;
    /**
     * 读取一个文件中的所有行。
     * <p>
     *     考虑到文件可能很大，所以每读一行就处理一行，如果有多行合并处理，业务需要自己写个状态机来合并
     * </p>
     * @param handleOneLine 处理一行文件
     * @throws IOException 出现I/O错误
     */
    public void forEach(Consumer<String> handleOneLine) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                handleOneLine.accept(line);
            }
        }
    }

    /**
     * 遍历CSV的每一行，需要输入一个间隔符
     * @param sep 间隔符，比如'\t'
     * @param handler 处理函数
     * @throws IOException 出现I/O错误
     */
    public void forEachCSV(String sep, Consumer<String[]> handler) throws IOException {
        forEach(s-> handler.accept(s.split(sep)));
    }
}
