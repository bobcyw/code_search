package com.caoyawen.code_search.infrastructure.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListBenchmark {
    void listBenchmark(List<Integer> l){
        for (int i = 0; i < BaseBenchmark.ELEMENTS; i++) {
            l.add(i);
        }
        for (int j = 0; j < BaseBenchmark.LOOP; j++) {
            for (int i = 0; i < BaseBenchmark.ELEMENTS; i++) {
                l.set(i, l.get(i)+1);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void linkedList(){
        listBenchmark(new LinkedList<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void arrayList(){
        listBenchmark(new ArrayList<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void copyOnWriteArrayList(){
        listBenchmark(new CopyOnWriteArrayList<>());
    }

    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder().include("ListBenchmark")
                .warmupIterations(1)
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
