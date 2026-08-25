package software.aoc.day05.b;

import java.util.List;

public record FreshRanges(List<FreshnessComponent> ranges) implements FreshnessComponent {
    public FreshRanges {
        ranges = List.copyOf(ranges);
    }

    public static FreshRanges from(String block) {
        List<FreshnessComponent> parsed = block.lines().map(String::trim).filter(line -> !line.isEmpty()).<FreshnessComponent>map(Range::parse).toList();
        return new FreshRanges(parsed);
    }

    @Override
    public boolean isFresh(long id) {
        return ranges.stream().anyMatch(range -> range.isFresh(id));
    }

    @Override
    public void accept(RangeVisitor visitor) {
        ranges.forEach(range -> range.accept(visitor));
    }
}
