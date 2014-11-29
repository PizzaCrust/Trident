package net.tridentsdk.server;

import com.google.common.collect.Sets;
import io.netty.util.internal.ConcurrentSet;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/*
Benchmark results: http://bit.ly/1y90tml
 */
@State(Scope.Benchmark)
public class CHMTest {
    private static final Set<Object> SET = new ConcurrentSet<>();
    private static final Set<Object> SET0 = Sets.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());

    private static final Object OBJECT = new Object();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + CHMTest.class.getSimpleName() + ".*") // CLASS
                .timeUnit(TimeUnit.NANOSECONDS)
                .mode(Mode.AverageTime)
                .warmupIterations(20)
                .warmupTime(TimeValue.milliseconds(1))              // ALLOWED TIME
                .measurementIterations(5)
                .measurementTime(TimeValue.milliseconds(1))         // ALLOWED TIME
                .forks(1)                                           // FORKS
                .verbosity(VerboseMode.SILENT)                      // GRAPH
                .threads(4)                                         // THREADS
                .build();

        Benchmarks.chart(Benchmarks.parse(new Runner(opt).run()), "Java8 CHM vs Platform CHM"); // TITLE
    }

    @Param({"1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024"})
    private int cpuTokens;

    @Benchmark
    public void control() {
        Blackhole.consumeCPU(cpuTokens);
    }

    @Benchmark
    public void v8CHMPut() {
        Blackhole.consumeCPU(cpuTokens);
        SET.add(OBJECT);
    }

    @Benchmark
    public void v8CHMRemove() {
        Blackhole.consumeCPU(cpuTokens);
        SET.remove(OBJECT);
    }

    @Benchmark
    public void CHMPut() {
        Blackhole.consumeCPU(cpuTokens);
        SET0.add(OBJECT);
    }

    @Benchmark
    public void CHMRemove() {
        Blackhole.consumeCPU(cpuTokens);
        SET0.remove(OBJECT);
    }
}
