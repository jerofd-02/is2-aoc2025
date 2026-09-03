package software.aoc.day10.a;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Machine(LightState target, List<Button> buttons) {
    private static final Pattern LIGHTS_PATTERN = Pattern.compile("\\[([.#]+)]");
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([0-9,]+)\\)");

    public Machine {
        buttons = List.copyOf(buttons);
    }

    public static Machine from(String line) {
        return new Machine(parseTarget(line), parseButtons(line));
    }

    private static LightState parseTarget(String line) {
        Matcher matcher = LIGHTS_PATTERN.matcher(line);
        if (!matcher.find()) throw new IllegalArgumentException("No light diagram found in line: " + line);
        String lights = matcher.group(1);

        int mask = 0;
        for (int i = 0; i < lights.length(); i++) {
            if (lights.charAt(i) == '#') {
                mask |= 1 << i;
            }
        }
        return new LightState(mask);
    }

    private static List<Button> parseButtons(String line) {
        Matcher matcher = BUTTON_PATTERN.matcher(line);
        List<Button> buttons = new ArrayList<>();
        while (matcher.find()) buttons.add(Button.from(matcher.group(1)));
        return buttons;
    }

    public long minPresses() {
        return new ButtonPressSearch(this).shortestPressCount();
    }
}
