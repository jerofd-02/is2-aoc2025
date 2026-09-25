package software.aoc.day02.a;

import software.aoc.day02.InvalidIdPattern;
import software.aoc.day02.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftShop {
    private static final String DOUBLED_BLOCK = "^(\\d+)\\1$";
    private final List<Range> ranges;
    private final InvalidIdPattern pattern;

    public GiftShop(InvalidIdPattern pattern) {
        this.ranges = new ArrayList<>();
        this.pattern = pattern;
    }

    public static GiftShop create() {
        return new GiftShop(InvalidIdPattern.of(DOUBLED_BLOCK));
    }

    public GiftShop add(String... ranges) {
        Arrays.stream(ranges).map(Range::from).forEach(this::add);
        return this;
    }

    public void add(Range range) {
        ranges.add(range);
    }

    public long sumOfInvalidIds() {
        return ranges.stream().flatMapToLong(range -> range.invalidIds(pattern)).sum();
    }

    public GiftShop execute(String input) {
        Arrays.stream(input.split("[\\r\\n,]+")).map(String::trim).filter(line -> !line.isEmpty()).forEach(this::add);
        return this;
    }
}
