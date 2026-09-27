package software.aoc.day08;

public record PairDistance(int first, int second, long distance) implements Comparable<PairDistance> {
    @Override
    public int compareTo(PairDistance other) {
        return Long.compare(distance, other.distance);
    }
}
