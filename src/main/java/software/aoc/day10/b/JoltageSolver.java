package software.aoc.day10.b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoltageSolver {
    static final double EPSILON = 1e-9;
    private final Machine machine;

    public JoltageSolver(Machine machine) {
        this.machine = machine;
    }

    private record SearchContext(List<Integer> freeColumns, ReducedSystem system, long upperBound) {
    }

    public long solveMinimumPresses() {
        ReducedSystem system = reduceToRref();

        if (system.hasInconsistentRow()) {
            throw new IllegalStateException("System has no solution for machine: " + machine);
        }

        List<Integer> freeColumns = freeColumns(system);
        int[] freeValues = new int[freeColumns.size()];
        SearchContext context = new SearchContext(freeColumns, system, upperBoundOnPresses());
        long best = searchFreeVariables(0, freeValues, context, context.upperBound());

        if (best == Long.MAX_VALUE) {
            throw new IllegalStateException("No valid integer combination found for machine: " + machine);
        }
        return best;
    }

    private ReducedSystem reduceToRref() {
        int numRows = machine.requirement().dimension();
        int numCols = machine.buttons().size();

        double[][] matrix = buildAugmentedMatrix(numRows, numCols);
        int[] pivotColumns = new int[numRows];
        Arrays.fill(pivotColumns, -1);

        int pivotRow = 0;
        for (int col = 0; col < numCols && pivotRow < numRows; col++) {
            int selected = selectPivotRow(matrix, pivotRow, col, numRows);
            if (Math.abs(matrix[selected][col]) < EPSILON) continue;

            swapRows(matrix, pivotRow, selected);
            eliminateColumn(matrix, pivotRow, col, numRows, numCols);
            pivotColumns[pivotRow] = col;
            pivotRow++;
        }

        return new ReducedSystem(matrix, pivotColumns, numRows, numCols);
    }

    private double[][] buildAugmentedMatrix(int numRows, int numCols) {
        double[][] matrix = new double[numRows][numCols + 1];
        List<Button> buttons = machine.buttons();

        for (int col = 0; col < numCols; col++) {
            Button button = buttons.get(col);
            for (int row = 0; row < numRows; row++) {
                if (button.affects(row)) matrix[row][col] = 1.0;
            }
        }
        for (int row = 0; row < numRows; row++) matrix[row][numCols] = machine.requirement().getTarget(row);
        return matrix;
    }

    private int selectPivotRow(double[][] matrix, int fromRow, int col, int numRows) {
        int selected = fromRow;
        for (int row = fromRow + 1; row < numRows; row++) {
            if (Math.abs(matrix[row][col]) > Math.abs(matrix[selected][col])) selected = row;
        }
        return selected;
    }

    private void swapRows(double[][] matrix, int a, int b) {
        double[] temp = matrix[a];
        matrix[a] = matrix[b];
        matrix[b] = temp;
    }

    private void eliminateColumn(double[][] matrix, int pivotRow, int col, int numRows, int numCols) {
        double divisor = matrix[pivotRow][col];
        for (int c = col; c <= numCols; c++) {
            matrix[pivotRow][c] /= divisor;
        }

        for (int row = 0; row < numRows; row++) {
            if (row == pivotRow) continue;
            double factor = matrix[row][col];
            if (Math.abs(factor) < EPSILON) continue;
            for (int c = col; c <= numCols; c++) matrix[row][c] -= factor * matrix[pivotRow][c];
        }
    }

    private List<Integer> freeColumns(ReducedSystem system) {
        boolean[] isPivot = new boolean[system.numCols()];
        for (int pivotCol : system.pivotColumns()) {
            if (pivotCol != -1) isPivot[pivotCol] = true;
        }

        List<Integer> free = new ArrayList<>();
        for (int col = 0; col < system.numCols(); col++) {
            if (!isPivot[col]) free.add(col);
        }
        return free;
    }

    private long upperBoundOnPresses() {
        return machine.requirement().targets().stream().mapToLong(Integer::longValue).sum();
    }

    private long searchFreeVariables(int freeIndex, int[] freeValues, SearchContext context, long bestSoFar) {
        if (freeIndex == context.freeColumns().size()) {
            long total = evaluateSolution(freeValues, context);
            return Math.min(bestSoFar, total);
        }

        long partialSum = sumOfAssignedFreeValues(freeValues, freeIndex);
        if (partialSum >= bestSoFar) return bestSoFar;

        long best = bestSoFar;
        for (int value = 0; value <= context.upperBound(); value++) {
            freeValues[freeIndex] = value;
            best = searchFreeVariables(freeIndex + 1, freeValues, context, best);
        }
        return best;
    }

    private long sumOfAssignedFreeValues(int[] freeValues, int upToExclusive) {
        long sum = 0;
        for (int i = 0; i < upToExclusive; i++) sum += freeValues[i];
        return sum;
    }

    private long evaluateSolution(int[] freeValues, SearchContext context) {
        ReducedSystem system = context.system();
        double[] x = new double[system.numCols()];
        for (int i = 0; i < context.freeColumns().size(); i++) x[context.freeColumns().get(i)] = freeValues[i];

        for (int row = 0; row < system.numRows(); row++) {
            int pivotCol = system.pivotColumns()[row];
            if (pivotCol == -1) continue;
            double value = system.matrix()[row][system.numCols()];
            for (int freeCol : context.freeColumns()) value -= system.matrix()[row][freeCol] * x[freeCol];
            x[pivotCol] = value;
        }

        return sumIfValidNonNegativeIntegers(x);
    }

    private long sumIfValidNonNegativeIntegers(double[] x) {
        long total = 0;
        for (double value : x) {
            long rounded = Math.round(value);
            if (value < -EPSILON || Math.abs(value - rounded) > EPSILON) return Long.MAX_VALUE;
            total += rounded;
        }
        return total;
    }
}