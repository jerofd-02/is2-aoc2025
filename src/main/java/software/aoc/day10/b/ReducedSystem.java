package software.aoc.day10.b;

public record ReducedSystem(double[][] matrix, int[] pivotColumns, int numRows, int numCols) {
    public boolean hasInconsistentRow() {
        for (int row = 0; row < numRows; row++) {
            if (pivotColumns[row] == -1 && Math.abs(matrix[row][numCols]) > JoltageSolver.EPSILON) return true;
        }
        return false;
    }
}