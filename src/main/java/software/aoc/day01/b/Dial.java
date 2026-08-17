package software.aoc.day01.b;

import software.aoc.day01.Rotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dial {
    private final static int INITIAL_POSITION = 50;
    private final static Cycle CYCLE = new Cycle(100);

    private final List<Rotation> rotations;

    private Dial() {
        this.rotations = new ArrayList<>();
    }

    public static Dial create() {
        return new Dial();
    }

    public Dial add(String... orders) {
        Arrays.stream(orders)
                .map(Rotation::from)
                .forEach(this::add);
        return this;
    }

    private void add(Rotation order) {
        rotations.add(order);
    }

    public int position() {
        return CYCLE.normalize(rawPosition(rotations.size()));
    }

    public int count() {
        return iterate()
                .map(this::zeroCrossingsAt)
                .sum();
    }

    private IntStream iterate() {
        return IntStream.rangeClosed(1, rotations.size()).parallel();
    }

    private int zeroCrossingsAt(int index) {
        return CYCLE.crossings(rawPosition(index - 1), rawPosition(index));
    }

    private int rawPosition(int size) {
        return sum(rotations.stream().limit(size));
    }

    private static int sum(Stream<Rotation> orders) {
        return orders.mapToInt(Rotation::step).sum() + INITIAL_POSITION;
    }

    public Dial execute(String orders) {
        return add(orders.split("\n"));
    }
}