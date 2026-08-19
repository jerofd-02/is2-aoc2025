package software.aoc.day02;

import java.util.stream.LongStream;

public record Range(long first, long last) {
    public static Range from(String range) {
        return parse(range);
    }

    public static Range parse(String input) {
        String[] bounds = input.trim().split("[,-]");
        return new Range(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
    }

    public LongStream invalidIds(InvalidIdPattern pattern) {
        return LongStream.rangeClosed(first, last).filter(pattern::matches);
    }
}