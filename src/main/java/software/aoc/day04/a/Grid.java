package software.aoc.day04.a;

import software.aoc.day04.Neighbors;
import software.aoc.day04.Position;

import java.util.List;

public record Grid(List<String> rows) {
    private static final char PAPER_ROLL = '@';
    private static final int MAX_NEIGHBORS_FOR_ACCESS = 4;

    public Grid {
        rows = List.copyOf(rows);
    }

    public static Grid from(String input) {
        List<String> lines = input.lines().map(String::trim).filter(line -> !line.isEmpty()).toList();
        return new Grid(lines);
    }

    public boolean isPaperRoll(Position position) {
        return contains(position) && charAt(position) == PAPER_ROLL;
    }


    private boolean contains(Position position) {
        return position.row() >= 0 && position.row() < rows.size() && position.col() >= 0 && position.col() < rows().get(position.row()).length();
    }

    private char charAt(Position position) {
        return rows.get(position.row()).charAt(position.col());
    }

    public boolean isAccessible(Position position) {
        return isPaperRoll(position) && paperNeighborsCount(position) < MAX_NEIGHBORS_FOR_ACCESS;
    }

    private long paperNeighborsCount(Position position) {
        long count = 0;

        for (Position neighbor : Neighbors.of(position)) {
            if (isPaperRoll(neighbor)) count++;
        }
        return count;
    }

    public long accessiblePaperRollsCount() {
        long count = 0;
        for (int row = 0; row < rows.size(); row++) {
            for (int col = 0; col < rows.get(row).length(); col++) {
                if (isAccessible(new Position(row, col))) count++;
            }
        }
        return count;
    }
}
