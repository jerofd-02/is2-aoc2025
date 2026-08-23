package software.aoc.day04.b;

import software.aoc.day04.Position;

import java.util.List;

public final class Forklifts {
    private static final Forklifts INSTANCE = new Forklifts();

    public Forklifts() {
    }

    public static Forklifts getInstance() {
        return INSTANCE;
    }

    public long totalRemovableRolls(Grid grid) {
        long total = 0;

        Grid current = grid;
        List<Position> accessible = current.accessiblePositions();

        while (!accessible.isEmpty()) {
            total += accessible.size();
            current = current.withRollsRemoved(accessible);
            accessible = current.accessiblePositions();
        }
        return total;
    }
}
