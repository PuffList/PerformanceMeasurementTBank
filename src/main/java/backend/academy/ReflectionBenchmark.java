package backend.academy;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * This class benchmarks different methods of invoking the method `name()` of the {@link Student} class:
 * 1. Direct method invocation.
 * 2. Reflection-based method invocation.
 * 3. Method invocation using {@link MethodHandles}.
 * 4. Method invocation using {@link LambdaMetafactory}.
 * The benchmarks measure the average time (in nanoseconds) for each invocation method using JMH.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class ReflectionBenchmark {

    private static final String METHOD_NAME = "name";
    private Student student;
    private Method method;
    private MethodHandle handle;
    private Function<Student, String> lambda;

    /**
     * Sets up the test environment. Prepares the {@link Student} object, reflection method,
     * {@link MethodHandle}, and {@link LambdaMetafactory}-based lambda function for benchmarking.
     *
     * @throws Throwable if any exception occurs during setup.
     */
    @Setup
    public void setup() throws Throwable {
        student = new Student("Alexander", "Lobov");
        method = Student.class.getMethod(METHOD_NAME);

        handle = MethodHandles.lookup()
            .findVirtual(Student.class, METHOD_NAME, MethodType.methodType(String.class));

        CallSite site = LambdaMetafactory.metafactory(
            MethodHandles.lookup(),
            "apply",
            MethodType.methodType(Function.class),
            MethodType.methodType(Object.class, Object.class),
            MethodHandles.lookup()
                .findVirtual(Student.class, METHOD_NAME, MethodType.methodType(String.class)),
            MethodType.methodType(String.class, Student.class)
        );

        lambda = (Function<Student, String>) site.getTarget().invokeExact();
    }

    /**
     * Benchmarks the direct method invocation.
     * The method `name()` is invoked directly on the {@link Student} object.
     *
     * @param bh a {@link Blackhole} to consume the result and prevent compiler optimizations.
     */
    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    /**
     * Benchmarks the method invocation using reflection.
     * The method `name()` is invoked using {@link Method#invoke(Object, Object...)}.
     *
     * @param bh a {@link Blackhole} to consume the result and prevent compiler optimizations.
     * @throws Throwable if any exception occurs during method invocation.
     */
    @Benchmark
    public void reflection(Blackhole bh) throws Throwable {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    /**
     * Benchmarks the method invocation using {@link MethodHandle}.
     * The method `name()` is invoked using a {@link MethodHandle}.
     *
     * @param bh a {@link Blackhole} to consume the result and prevent compiler optimizations.
     * @throws Throwable if any exception occurs during method invocation.
     */
    @Benchmark
    public void methodHandles(Blackhole bh) throws Throwable {
        String name = (String) handle.invoke(student);
        bh.consume(name);
    }

    /**
     * Benchmarks the method invocation using {@link LambdaMetafactory}.
     * A lambda function is dynamically created to invoke the method `name()` and is then executed.
     *
     * @param bh a {@link Blackhole} to consume the result and prevent compiler optimizations.
     */
    @Benchmark
    public void lambdaMetaFactory(Blackhole bh) {
        String name = lambda.apply(student);
        bh.consume(name);
    }
}
