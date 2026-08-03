package software.aoc.day01.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dial {
    private final static int INITIAL_POSITION = 50;
    private final static int DIAL_SIZE = 100;

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
        return normalize(sumAll());
    }

    private int sumAll() {
        return sum(rotations.stream());
    }

    public int count() {
        return (int) iterate()
                .map(this::sumPartial)
                .filter(s -> s == 0)
                .count();
    }

    private IntStream iterate() {
        return IntStream.rangeClosed(1, rotations.size()).parallel();
    }

    private int sumPartial(int size) {
        return normalize(sum(rotations.stream().limit(size)));
    }

    private static int sum(Stream<Rotation> orders) {
        return orders.mapToInt(Rotation::step).sum() + INITIAL_POSITION;
    }

    private int normalize(int value) {
        return ((value < 0 ? DIAL_SIZE : 0) + value % DIAL_SIZE) % DIAL_SIZE;
    }

    public Dial execute(String orders) {
        return add(orders.split("\n"));
    }
}