package com.caoyawen.code_search.infrastructure.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class SetBenchmark {
    void setBenchmark(Set<Integer> s){
        for (int i = 0; i < BaseBenchmark.ELEMENTS; i++) {
            s.add(i);
        }
        for (int j = 0; j < BaseBenchmark.LOOP; j++) {
            for (int i = 0; i < BaseBenchmark.ELEMENTS; i++) {
                if(s.contains(i+j*BaseBenchmark.ELEMENTS)){
                    s.remove(i + j * BaseBenchmark.ELEMENTS);
                    s.add(i + (j + 1) * BaseBenchmark.ELEMENTS);
                }
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void hashSet(){
        setBenchmark(new HashSet<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void linkedHashSet(){
        setBenchmark(new LinkedHashSet<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void concurrentSkipListSet(){
        setBenchmark(new ConcurrentSkipListSet<>());
    }

    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder().include("SetBenchmark")
                .warmupIterations(1)
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
