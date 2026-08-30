package software.aoc.day07.a;

import software.aoc.day07.Grid;

import java.util.HashSet;
import java.util.Set;

public record BeamState(Set<Integer> columns, long splits) {
    public BeamState {
        columns = Set.copyOf(columns);
    }

    public static BeamState startingAt(int column) {
        return new BeamState(Set.of(column), 0);
    }

    public BeamState advanceThrough(Grid grid, Integer row) {
        Set<Integer> nextColumns = new HashSet<>();
        long newSplits = 0;

        for (int column : columns) {
            if (grid.isSplitter(row, column)) {
                newSplits++;
                addIfInBounds(nextColumns, grid, row, column - 1);
                addIfInBounds(nextColumns, grid, row, column + 1);
            } else {
                nextColumns.add(column);
            }
        }
        return new BeamState(nextColumns, splits + newSplits);
    }

    private void addIfInBounds(Set<Integer> target, Grid grid, int row, int column) {
        if (grid.isInBounds(row, column)) target.add(column);
    }
}
