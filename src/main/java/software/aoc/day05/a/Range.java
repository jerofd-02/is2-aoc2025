package software.aoc.day05.a;

import software.aoc.day05.FreshnessRule;

public record Range(long first, long last) implements FreshnessRule {
    public static Range parse(String line) {
        String[] bounds = line.trim().split("-");
        return new Range(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
    }

    @Override
    public boolean isFresh(long id) {
        return id >= first && id <= last;
    }
}
