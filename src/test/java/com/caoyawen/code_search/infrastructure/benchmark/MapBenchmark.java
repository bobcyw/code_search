package com.caoyawen.code_search.infrastructure.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * JMH的例子程序，用来写各种可能的注解
 */
public class MapBenchmark{
    void mapTest(Map<Integer, Integer> c){
        for (int i = 0; i < BaseBenchmark.ELEMENTS; i++) {
            c.put(i, i);
        }
        for (int j = 0; j < BaseBenchmark.LOOP; j++) {
            for (int i = 0; i < BaseBenchmark.ELEMENTS; i++) {
                c.put(i, c.get(i) + 1);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void hashMap(){
        mapTest(new HashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void concurrentHashMap(){
        mapTest(new ConcurrentHashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void hashTable(){
        mapTest(new Hashtable<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void linkedHashMap(){
        mapTest(new LinkedHashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void concurrentSkipListMap(){
        mapTest(new ConcurrentSkipListMap<>());
    }

    public static void main(String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder().include("MapBenchmark")
                .warmupIterations(1)
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
