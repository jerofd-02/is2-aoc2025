package software.aoc.day04;

public record Position(int row, int col) {
    public Position plus(int deltaRow, int deltaCol) {
        return new Position(row + deltaRow, col + deltaCol);
    }
}
