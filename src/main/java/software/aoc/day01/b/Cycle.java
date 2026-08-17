package software.aoc.day01.b;

public record Cycle (int size) {
    public int normalize(int value) {
        return ((value < 0 ? size : 0) + value % size) % size;
    }

    public int crossings(int before, int after) {
        if (after == before) return 0;
        return after > before
                ? Math.floorDiv(after, size) - Math.floorDiv(before, size)
                : Math.floorDiv(before - 1, size) - Math.floorDiv(after - 1, size);
    }
}
