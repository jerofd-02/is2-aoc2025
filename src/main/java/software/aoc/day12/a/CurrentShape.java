package software.aoc.day12.a;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record CurrentShape(int index, List<Set<Point>> orientations) {
    public CurrentShape {
        orientations = List.copyOf(orientations);
    }

    public static CurrentShape from(int index, List<String> diagramLines) {
        Set<Point> cells = new HashSet<>();
        for (int row = 0; row < diagramLines.size(); row++) {
            String line = diagramLines.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') cells.add(new Point(row, col));
            }
        }
        return new CurrentShape(index, uniqueOrientationsOf(cells));
    }

    public int area() {
        return orientations.getFirst().size();
    }

    private static List<Set<Point>> uniqueOrientationsOf(Set<Point> baseCells) {
        Set<Set<Point>> unique = new HashSet<>();
        for (int flip = 0; flip < 2; flip++) {
            Set<Point> variant = flip == 0 ? baseCells : mirror(baseCells);
            for (int rotation = 0; rotation < 4; rotation++) {
                unique.add(variant);
                variant = rotateClockwise(variant);
            }
        }
        return List.copyOf(unique);
    }

    private static Set<Point> rotateClockwise(Set<Point> points) {
        Set<Point> rotated = new HashSet<>();
        for (Point p : points) rotated.add(new Point(p.y(), -p.x()));
        return normalize(rotated);
    }

    private static Set<Point> mirror(Set<Point> points) {
        Set<Point> mirrored = new HashSet<>();
        for (Point p : points) mirrored.add(new Point(p.x(), -p.y()));
        return normalize(mirrored);
    }

    private static Set<Point> normalize(Set<Point> points) {
        int minRow = points.stream().mapToInt(Point::x).min().orElse(0);
        int minCol = points.stream().mapToInt(Point::y).min().orElse(0);
        Set<Point> normalized = new HashSet<>();
        for (Point p : points) normalized.add(new Point(p.x() - minRow, p.y() - minCol));
        return Set.copyOf(normalized);
    }
}
