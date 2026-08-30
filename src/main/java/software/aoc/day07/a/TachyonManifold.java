package software.aoc.day07.a;

import software.aoc.day07.Grid;

import java.util.stream.IntStream;

public record TachyonManifold(Grid grid) {
    public static TachyonManifold from(String input) {
        return new TachyonManifold(Grid.from(input));
    }

    public long countSplits() {
        BeamState initialState = BeamState.startingAt(grid.startColumn());

        BeamState finalState = IntStream.range(1, grid.rowCount())
                .boxed()
                .reduce(initialState, (state, row) -> state.advanceThrough(grid, row), (a, b) -> a);

        return finalState.splits();
    }
}
