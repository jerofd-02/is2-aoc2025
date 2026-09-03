package software.aoc.day10.a;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class ButtonPressSearch {
    private final Machine machine;

    public ButtonPressSearch(Machine machine) {
        this.machine = machine;
    }

    public long shortestPressCount() {
        int targetMask = machine.target().mask();
        if (targetMask == 0) return 0;

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> currentLevel = new ArrayDeque<>();
        visited.add(0);
        currentLevel.add(0);

        long presses = 0;
        while (!currentLevel.isEmpty()) {
            presses++;
            Queue<Integer> nextLevel = new ArrayDeque<>();

            for (int mask : currentLevel) {
                for (Button button : machine.buttons()) {
                    int next = mask ^ button.mask();
                    if (next == targetMask) return presses;
                    if (visited.add(next)) nextLevel.add(next);
                }
            }
            currentLevel = nextLevel;
        }
        throw new IllegalStateException("No sequence of button presses reaches the target configuration");
    }
}
