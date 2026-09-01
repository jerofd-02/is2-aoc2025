package software.aoc.day09;

public record RedTile(long x, long y) {
    public static RedTile from(String line) {
        String[] parts = line.split(",");
        return new RedTile(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }
}
