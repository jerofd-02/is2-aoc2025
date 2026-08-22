package software.aoc.day03.b;

public record Bank(String digits) {
    public static Bank from(String line) {
        return new Bank(line.trim());
    }

    public long maxJoltage(JoltageCalculator joltage) {
        return joltage.compute(digits);
    }
}
