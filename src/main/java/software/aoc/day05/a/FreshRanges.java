package software.aoc.day05.a;

import java.util.List;

public record FreshRanges(List<FreshnessRule> ranges) implements FreshnessRule {
    public FreshRanges {
        ranges = List.copyOf(ranges);
    }

    public static FreshRanges from(String block) {
        List<FreshnessRule> parsed = block.lines().map(String::trim).filter(line -> !line.isEmpty()).<FreshnessRule>map(Range::parse).toList();
        return new FreshRanges(parsed);
    }

    @Override
    public boolean isFresh(long id) {
        return ranges.stream().anyMatch(range -> range.isFresh(id));
    }
}
