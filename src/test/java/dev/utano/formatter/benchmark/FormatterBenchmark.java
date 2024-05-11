package dev.utano.formatter.benchmark;

import dev.utano.formatter.DefaultFormatter;
import dev.utano.formatter.Formatter;
import dev.utano.formatter.PlaceHolder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Fork(value = 1, warmups = 1)
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.SECONDS)
public class FormatterBenchmark {

	private Formatter formatter;
	private int iterations;
	private String textToFormatJava;
	private String textToFormatKeyValue;
	private String textToFormatQuick;

	PlaceHolder[] placeHolders;
	private Object[] objects;

	@Setup(Level.Trial)
	public void setup() {
		formatter = DefaultFormatter.getFormatter();
		iterations = 10000000;
		textToFormatJava = "Hello %s this %d is %d a %s test!!!";
		textToFormatKeyValue = "Hello {NAME} this {EX} is {EX2} a {EX3} test!!";
		textToFormatQuick = "Hello % this % is % a % test!!";
		placeHolders = new PlaceHolder[]{new PlaceHolder("NAME", "your{EX}name"), new PlaceHolder("EX", 44), new PlaceHolder("EX2", 11), new PlaceHolder("EX3", "great")};
		objects = new Object[]{"yourname", 44, 11, "great"};
	}

	@Benchmark
	public String javaFormatterBenchmark() {
		String tmp = null; // Prevent simplification by removal
		for (int i = 0; i < iterations; i++)
			tmp = String.format(textToFormatJava, objects);
		return tmp;
	}

	@Benchmark
	public String keyValuePlaceHolderBenchmark() {
		String tmp = null; // Prevent simplification by removal
		for (int i = 0; i < iterations; i++)
			tmp = formatter.format(textToFormatKeyValue, placeHolders);
		return tmp;
	}

	@Benchmark
	public String quickFormatBenchmark() {
		String tmp = null; // Prevent simplification by removal
		for (int i = 0; i < iterations; i++)
			tmp = formatter.format(textToFormatQuick, objects);
		return tmp;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(FormatterBenchmark.class.getSimpleName())
				.forks(1)
				.warmupIterations(1)
				.build();

		new Runner(opt).run();
	}
}