package software.aoc.day01;

public record Rotation(int step) {
    public static Rotation from(String order) {
        return parse(order);
    }

    private static Rotation parse(String order) {
        return new Rotation(signOf(order) * valueOf(order));
    }

    private static int signOf(String order) {
        return order.charAt(0) == 'L' ? -1 : 1;
    }

    private static int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }
}
