package software.aoc.day07.b;

import software.aoc.day07.Grid;

import java.util.stream.IntStream;

public record QuantumManifold(Grid grid) {
    public static QuantumManifold from(String input) {
        return new QuantumManifold(Grid.from(input));
    }

    public long countTimelines() {
        TimelineState initialState = TimelineState.startingAt(grid.startColumn());

        TimelineState finalState = IntStream.range(1, grid.rowCount()).boxed().reduce(initialState, (state, row) -> state.advanceThrough(grid, row), (a, b) -> a);

        return finalState.total();
    }
}
