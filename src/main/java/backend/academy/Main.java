package backend.academy;

import lombok.experimental.UtilityClass;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@UtilityClass
public class Main {

    private static final int WARMUP_ITERATIONS_TIME = 5;
    private static final int MEASUREMENT_ITERATIONS_TIME = 5;

    /**
     * Entry point for running JMH benchmarks.
     * This method configures and runs the benchmark tests.
     *
     * @param args command-line arguments (not used).
     * @throws RunnerException if the benchmark execution fails.
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .forks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(WARMUP_ITERATIONS_TIME))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(MEASUREMENT_ITERATIONS_TIME))
            .resultFormat(ResultFormatType.TEXT) // Формат вывода
            .result("src/main/resources/benchmark-results.txt")
            .build();

        new Runner(options).run();
    }
}
