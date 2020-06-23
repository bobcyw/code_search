package com.caoyawen.code_search.application.some_group.jinshan;

import com.caoyawen.code_search.infrastructure.file.Loop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 金山cwiki相关的报告处理
 */
public class Report {
    /**
     * 存储老报告的内容
     */
    private final List<Line> lines = new LinkedList<>();

    public List<Line> getLines() {
        return lines;
    }

    /**
     * 读取老报告，并存到lines里，供{@link #mergeReport(List)}使用
     * @throws IOException 如果读取失败，则抛出错误
     */
    public void readOldReport(Loop reader) throws IOException {
//        final FileLoop reader = new FileLoop("/Users/caoyawen/data/jinshan/origin.txt");
        reader.forEach(l -> lines.add(new Line(trimSpaceInLine(l))));
    }


    /**
     * 类似{@link #mergeReport(List)}，不同点是暴露了新数据的处理方式.
     * 方便函数处理
     * @param scanResults  新的扫描结果
     * @param handle 一个对新结果进行处理的函数
     */
    private void mergeReport(List<String> scanResults, Consumer<String> handle){
        int statusCap = lines.get(0).getStatus().size()+1;
        LinkedHashMap<String, Line> currentM = new LinkedHashMap<>();
        //生成新的report集合
        lines.forEach(l->currentM.put(l.getFileContent(), l));
        scanResults.forEach(r->{
            Line find;
            if((find = currentM.get(r)) != null){
                find.getStatus().add("exist");
            }else{
                currentM.put(r, Line.newByFile(r, statusCap));
            }
        });
        //将新的report集合写入文件
        currentM.forEach((k, v)->handle.accept(v.toString()));
    }

    public Report(BufferedWriter reportWriter) {
        this.reportWriter = reportWriter;
    }

    /**
     * 报告输出的地方，由外部调用者给出
     */
    BufferedWriter reportWriter;

    /**
     * 将{@link #lines}包含的历史数据和新的扫描结果进行合并，得到一个新文件，并写出去。
     * <p>
     * 这里有一个比较细节的地方，因为Consumer是不带exception签名的，所以，遇到exception的情况需要用RunTimeException输出，然后再看用{@link AbortException#getCause()}把结果捞出来，用instanceof来判断是不是期望的exception
     * @param scanResults 新的扫描结果
     * @throws IOException 记录如果IO出错的异常
     */
    public void mergeReport(List<String> scanResults) throws IOException{
        try{
            mergeReport(scanResults, str -> {
                try {
                    reportWriter.write(str);
                } catch (IOException e) {
                    throw new AbortException(e);
                }
            });
        }catch (AbortException e){
            if (e.getCause() instanceof IOException) {
                //如果可以处理，就按照IOException来处理
                throw (IOException) e.getCause();
            }
            //未知错误就按照IOException
            throw e;
        }
    }



    enum Status {
        NORMAL,
        STATUS
    }

    private String trimSpaceInLine(String line){
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
}
