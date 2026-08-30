package software.aoc.day09.a;

public record Rectangle(RedTile first, RedTile second) {
    public static Rectangle between(RedTile first, RedTile second) {
        return new Rectangle(first, second);
    }

    public long area() {
        long width = Math.abs(first.x() - second.x()) + 1;
        long height = Math.abs(first.y() - second.y()) + 1;
        return width * height;
    }
}
