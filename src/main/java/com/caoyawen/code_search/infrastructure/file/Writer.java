package com.caoyawen.code_search.infrastructure.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * 支持try-with-resource的文件写入，同时支持函式编程
 */
public class Writer implements AutoCloseable{
    String fileName;
    BufferedWriter fileWriter;

    public Writer(String fileName) throws IOException {
        this.fileName = fileName;
        fileWriter = new BufferedWriter(new FileWriter(fileName));
    }

    public void writeLine(Supplier<String> input) throws IOException {
        fileWriter.write(input.get());
    }

    /**
     * 注意一下，用这个函数是直接调用RunTimeException，会直接导致JVM停机，只有数据一致性比较重要的时候才调用
     * @param input 输入一个supplier，比如toString之类的
     * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html">Unchecked Exceptions — The Controversy</a>
     * @see <a href="https://stackoverflow.com/questions/25643348/java-8-method-reference-unhandled-exception">java 8 method reference unhandled exception</a>
     */
    public void writeLineNoException(String input){
        try {
            fileWriter.write(input);
        } catch (IOException e) {
            throw new AbortException("write line meet IOException", e);
        }
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }
}
