package software.aoc.day09.b;

import software.aoc.day09.RedTile;

import java.util.ArrayList;
import java.util.List;

public record Polygon(List<RedTile> vertices) {
    public Polygon {
        vertices = List.copyOf(vertices);
    }

    public static Polygon from(List<RedTile> orderedRedTiles) {
        return new Polygon(orderedRedTiles);
    }

    public boolean contains(Rectangle rectangle) {
        return centerIsInside(rectangle) && noEdgeCrossesInterior(rectangle);
    }

    private List<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edges.add(new Edge(vertices.get(i), vertices.get((i + 1) % vertices.size())));
        }
        return edges;
    }

    private boolean centerIsInside(Rectangle rectangle) {
        double centerX = (rectangle.minX() + rectangle.maxX()) / 2.0;
        double centerY = (rectangle.minY() + rectangle.maxY()) / 2.0;

        long crossings = edges().stream().filter(Edge::isVertical).filter(edge -> straddlesY(edge, centerY)).filter(edge -> edge.start().x() > centerX).count();

        return crossings % 2 == 1;
    }

    private boolean straddlesY(Edge edge, double y) {
        long yMin = Math.min(edge.start().y(), edge.end().y());
        long yMax = Math.max(edge.start().y(), edge.end().y());
        return y >= yMin && y < yMax;
    }

    private boolean noEdgeCrossesInterior(Rectangle rectangle) {
        return edges().stream().noneMatch(edge -> crossesInterior(edge, rectangle));
    }

    private boolean crossesInterior(Edge edge, Rectangle rectangle) {
        if (edge.isVertical()) {
            long x = edge.start().x();
            if (x <= rectangle.minX() || x >= rectangle.maxX()) return false;
            long yMin = Math.min(edge.start().y(), edge.end().y());
            long yMax = Math.max(edge.start().y(), edge.end().y());
            return overlaps(yMin, yMax, rectangle.minY(), rectangle.maxY());
        }

        long y = edge.start().y();
        if (y <= rectangle.minY() || y >= rectangle.maxY()) return false;
        long xMin = Math.min(edge.start().x(), edge.end().x());
        long xMax = Math.max(edge.start().x(), edge.end().x());
        return overlaps(xMin, xMax, rectangle.minX(), rectangle.maxX());
    }

    private boolean overlaps(long aMin, long aMax, long bMin, long bMax) {
        return Math.max(aMin, bMin) < Math.min(aMax, bMax);
    }
}
