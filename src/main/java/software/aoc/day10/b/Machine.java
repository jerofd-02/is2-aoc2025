package software.aoc.day10.b;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Machine(JoltageRequirement requirement, List<Button> buttons) {
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([0-9,]+)\\)");
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([0-9,]+)}");

    public Machine {
        buttons = List.copyOf(buttons);
    }

    public static Machine from(String line) {
        return new Machine(parseRequirement(line), parseButtons(line));
    }

    private static JoltageRequirement parseRequirement(String line) {
        Matcher matcher = JOLTAGE_PATTERN.matcher(line);
        if (!matcher.find()) throw new IllegalArgumentException("No joltage requirement found in line: " + line);
        return JoltageRequirement.from(matcher.group(1));
    }

    private static List<Button> parseButtons(String line) {
        Matcher matcher = BUTTON_PATTERN.matcher(line);
        List<Button> buttons = new ArrayList<>();
        while (matcher.find()) buttons.add(Button.from(matcher.group(1)));
        return buttons;
    }

    public long minPresses() {
        return new JoltageSolver(this).solveMinimumPresses();
    }
}