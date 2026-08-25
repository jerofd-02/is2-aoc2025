package software.aoc.day05.b;

public record Range(long first, long last) implements FreshnessComponent {
    public static Range parse(String line) {
        String[] bounds = line.trim().split("-");
        return new Range(Long.parseLong(bounds[0]), Long.parseLong(bounds[1]));
    }

    @Override
    public boolean isFresh(long id) {
        return id >= first && id <= last;
    }

    @Override
    public void accept(RangeVisitor visitor) {
        visitor.visit(this);
    }

    public long length() {
        return last - first + 1;
    }

    public boolean overlaps(Range other) {
        return other.first() <= this.last;
    }

    public Range mergedWith(Range other) {
        return new Range(first, Math.max(last, other.last()));
    }
}
