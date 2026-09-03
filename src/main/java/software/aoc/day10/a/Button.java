package software.aoc.day10.a;

public record Button(int mask) {
    public static Button from(String indexes) {
        int mask = 0;
        for (String index : indexes.split(",")) {
            mask |= 1 << Integer.parseInt(index.trim());
        }
        return new Button(mask);
    }
}
