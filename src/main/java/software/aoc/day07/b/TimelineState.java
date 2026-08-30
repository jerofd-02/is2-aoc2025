package software.aoc.day07.b;

import software.aoc.day07.Grid;

import java.util.HashMap;
import java.util.Map;

public record TimelineState(Map<Integer, Long> timelinesByColumn) {
    public TimelineState {
        timelinesByColumn = Map.copyOf(timelinesByColumn);
    }

    public static TimelineState startingAt(int column) {
        return new TimelineState(Map.of(column, 1L));
    }

    public TimelineState advanceThrough(Grid grid, Integer row) {
        Map<Integer, Long> next = new HashMap<>();
        timelinesByColumn.forEach((column, count) -> {
            if (grid.isSplitter(row, column)) {
                addIfInBounds(next, grid, row, column - 1, count);
                addIfInBounds(next, grid, row, column + 1, count);
            } else {
                next.merge(column, count, Long::sum);
            }
        });
        return new TimelineState(next);
    }

    private void addIfInBounds(Map<Integer, Long> target, Grid grid, int row, int column, long count) {
        if (grid.isInBounds(row, column)) target.merge(column, count, Long::sum);
    }

    public long total() {
        return timelinesByColumn.values().stream().mapToLong(Long::longValue).sum();
    }
}
