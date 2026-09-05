package software.aoc.day12.a;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Region(int width, int length, List<Integer> requiredCounts) {
    private static final Pattern REGION_PATTERN = Pattern.compile("(\\d+)x(\\d+):\\s*(.+)");

    public Region {
        requiredCounts = List.copyOf(requiredCounts);
    }

    public static Region from(String line) {
        Matcher matcher = REGION_PATTERN.matcher(line);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid region line: " + line);
        int width = Integer.parseInt(matcher.group(1));
        int length = Integer.parseInt(matcher.group(2));
        List<Integer> counts = Arrays.stream(matcher.group(3).trim().split("\\s+")).map(Integer::parseInt).toList();
        return new Region(width, length, counts);
    }
}
