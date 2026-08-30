package software.aoc.day08;

public record JunctionBox(long x, long y, long z) {
    public static JunctionBox from(String line) {
        String[] parts = line.split(",");
        return new JunctionBox(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }

    public double distanceSquaredTo(JunctionBox other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
    }
}
