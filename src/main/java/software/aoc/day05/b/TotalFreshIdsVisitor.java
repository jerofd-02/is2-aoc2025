package software.aoc.day05.b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class TotalFreshIdsVisitor implements RangeVisitor {
    private final List<Range> visited = new ArrayList<>();

    @Override
    public void visit(Range range) {
        visited.add(range);
    }

    public long total() {
        return mergeOverlapping(sortedByFirst()).stream().mapToLong(Range::length).sum();
    }

    private List<Range> sortedByFirst() {
        return visited.stream().sorted(Comparator.comparingLong(Range::first)).toList();
    }

    private List<Range> mergeOverlapping(List<Range> sorted) {
        List<Range> merged = new ArrayList<>();
        for (Range range : sorted) {
            if (canMergeWithLast(merged, range)) {
                merged.set(merged.size() - 1, merged.getLast().mergedWith(range));
            } else {
                merged.add(range);
            }
        }
        return merged;
    }

    private boolean canMergeWithLast(List<Range> merged, Range range) {
        return !merged.isEmpty() && merged.getLast().overlaps(range);
    }
}
