package software.aoc.day08.b;

import software.aoc.day08.JunctionBox;

public record LastConnection(JunctionBox first, JunctionBox second) {
    public long xProduct() {
        return first.x() * second.x();
    }
}
