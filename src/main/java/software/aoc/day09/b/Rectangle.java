package software.aoc.day09.b;

import software.aoc.day09.RedTile;

public record Rectangle(RedTile first, RedTile second) {
    public static Rectangle between(RedTile first, RedTile second) {
        return new Rectangle(first, second);
    }

    public long area() {
        return (maxX() - minX() + 1) * (maxY() - minY() + 1);
    }

    public long minX() {
        return Math.min(first.x(), second.x());
    }

    public long maxX() {
        return Math.max(first.x(), second.x());
    }

    public long minY() {
        return Math.min(first.y(), second.y());
    }

    public long maxY() {
        return Math.max(first.y(), second.y());
    }
}