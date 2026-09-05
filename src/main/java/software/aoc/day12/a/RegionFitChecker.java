package software.aoc.day12.a;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class RegionFitChecker {
    private RegionFitChecker() {
    }

    public static boolean fits(Region region, List<CurrentShape> catalog) {
        boolean[][] occupied = new boolean[region.length()][region.width()];
        int[] remaining = region.requiredCounts().stream().mapToInt(Integer::intValue).toArray();

        int[] shapeAreas = catalog.stream().mapToInt(CurrentShape::area).toArray();
        int totalCells = region.width() * region.length();

        return solve(occupied, remaining, catalog, region, shapeAreas, totalCells, 0);
    }

    private static boolean solve(boolean[][] occupied, int[] remaining, List<CurrentShape> catalog, Region region, int[] shapeAreas, int totalCells, int occupiedCount) {
        if (allZero(remaining)) return true;
        int remainingArea = remainingArea(remaining, shapeAreas);
        int freeCells = totalCells - occupiedCount;

        if (remainingArea > freeCells) return false;
        Optional<Point> targetCell = firstEmptyCell(occupied, region);

        if (targetCell.isEmpty()) return false;
        Point target = targetCell.get();

        if (tryPlacingAnyPiece(occupied, remaining, catalog, region, target, shapeAreas, totalCells, occupiedCount)) {
            return true;
        }
        return trySkippingCell(occupied, remaining, catalog, region, target, shapeAreas, totalCells, occupiedCount);
    }

    private static int remainingArea(int[] remaining, int[] shapeAreas) {
        int total = 0;
        for (int i = 0; i < remaining.length; i++) total += remaining[i] * shapeAreas[i];
        return total;
    }

    private static boolean tryPlacingAnyPiece(boolean[][] occupied, int[] remaining, List<CurrentShape> catalog, Region region, Point target, int[] shapeAreas, int totalCells, int occupiedCount) {
        for (CurrentShape shape : catalog) {
            if (remaining[shape.index()] <= 0) continue;
            for (Set<Point> orientation : shape.orientations()) {
                if (tryOrientationAt(orientation, target, occupied, remaining, catalog, region, shape.index(), shapeAreas, totalCells, occupiedCount)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean tryOrientationAt(Set<Point> orientation, Point target, boolean[][] occupied, int[] remaining, List<CurrentShape> catalog, Region region, int shapeIndex, int[] shapeAreas, int totalCells, int occupiedCount) {
        for (Point anchor : orientation) {
            int originRow = target.x() - anchor.x();
            int originCol = target.y() - anchor.y();

            if (!canPlace(orientation, occupied, region, originRow, originCol)) continue;
            setOccupied(orientation, occupied, originRow, originCol, true);
            remaining[shapeIndex]--;

            if (solve(occupied, remaining, catalog, region, shapeAreas, totalCells, occupiedCount + orientation.size())) {
                return true;
            }
            remaining[shapeIndex]++;
            setOccupied(orientation, occupied, originRow, originCol, false);
        }
        return false;
    }

    private static boolean trySkippingCell(boolean[][] occupied, int[] remaining, List<CurrentShape> catalog, Region region, Point target, int[] shapeAreas, int totalCells, int occupiedCount) {
        occupied[target.x()][target.y()] = true;
        boolean success = solve(occupied, remaining, catalog, region, shapeAreas, totalCells, occupiedCount + 1);
        occupied[target.x()][target.y()] = false;
        return success;
    }

    private static boolean canPlace(Set<Point> orientation, boolean[][] occupied, Region region, int originRow, int originCol) {
        for (Point p : orientation) {
            int row = originRow + p.x();
            int col = originCol + p.y();
            if (row < 0 || row >= region.length() || col < 0 || col >= region.width() || occupied[row][col])
                return false;
        }
        return true;
    }

    private static void setOccupied(Set<Point> orientation, boolean[][] occupied, int originRow, int originCol, boolean value) {
        for (Point p : orientation) occupied[originRow + p.x()][originCol + p.y()] = value;
    }

    private static Optional<Point> firstEmptyCell(boolean[][] occupied, Region region) {
        for (int row = 0; row < region.length(); row++) {
            for (int col = 0; col < region.width(); col++) {
                if (!occupied[row][col]) return Optional.of(new Point(row, col));
            }
        }
        return Optional.empty();
    }

    private static boolean allZero(int[] values) {
        for (int value : values) {
            if (value != 0) return false;
        }
        return true;
    }
}