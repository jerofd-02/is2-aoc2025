package software.aoc.day09.b;

import software.aoc.day09.RedTile;

public record Edge(RedTile start, RedTile end) {
    public boolean isVertical() {
        return start.x() == end.x();
    }
}
