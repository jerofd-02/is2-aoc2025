package software.aoc.day02.a;

import java.util.regex.Pattern;
import java.util.stream.LongStream;

public record Range(long first, long last) {
    private static final Pattern REPEATED_TWICE = Pattern.compile("^(\\d+)\\1$");

    public static Range from(String range) {
        return parse(range);
    }

    public static Range parse(String input) {
        String[] bounds = input.trim().split("[,-]");
        return new Range(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
    }

    public LongStream invalidIds() {
        return LongStream.rangeClosed(first, last).filter(Range::isInvalid);
    }

    private static boolean isInvalid(long id) {
        return REPEATED_TWICE.matcher(Long.toString(id)).matches();
    }
}