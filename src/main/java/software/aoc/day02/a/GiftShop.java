package software.aoc.day02.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftShop {
    private final List<Range> ranges;

    public GiftShop() {
        this.ranges = new ArrayList<>();
    }

    public static GiftShop create() {
        return new GiftShop();
    }

    public GiftShop add(String... ranges) {
        Arrays.stream(ranges)
                .map(Range::from)
                .forEach(this::add);
        return this;
    }

    public void add(Range range) {
        ranges.add(range);
    }

    public long sumOfInvalidIds() {
        return ranges.stream()
                .flatMapToLong(Range::invalidIds)
                .sum();
    }

    public GiftShop execute(String input) {
        Arrays.stream(input.split("[\\r\\n,]+"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .forEach(this::add);
        return this;
    }
}
