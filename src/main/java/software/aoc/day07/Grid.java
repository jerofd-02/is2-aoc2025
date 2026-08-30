package software.aoc.day07;

import java.util.List;

public record Grid(List<String> rows) {
    public Grid {
        rows = List.copyOf(rows);
    }

    public static Grid from(String input) {
        return new Grid(input.lines().filter(line -> !line.isBlank()).toList());
    }

    public int rowCount() {
        return rows.size();
    }

    public int startColumn() {
        int column = rows.getFirst().indexOf('S');
        if (column == -1) {
            throw new IllegalStateException("No start position ('S') found on the first row");
        }
        return column;
    }

    public boolean isSplitter(int row, int column) {
        return isInBounds(row, column) && rows.get(row).charAt(column) == '^';
    }

    public boolean isInBounds(int row, int column) {
        return column >= 0 && column < rows.get(row).length();
    }
}
