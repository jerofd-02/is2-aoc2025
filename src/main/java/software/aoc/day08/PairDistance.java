package software.aoc.day08;

public record PairDistance(int first, int second, long distanceSquared) implements Comparable<PairDistance> {
    @Override
    public int compareTo(PairDistance other) {
        return Long.compare(distanceSquared, other.distanceSquared);
    }
}
