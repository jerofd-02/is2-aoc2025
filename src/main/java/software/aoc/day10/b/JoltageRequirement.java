package software.aoc.day10.b;

import java.util.Arrays;
import java.util.List;

public record JoltageRequirement(List<Integer> targets) {
    public JoltageRequirement {
        targets = List.copyOf(targets);
    }

    public static JoltageRequirement from(String rawTargets) {
        List<Integer> targetList = Arrays.stream(rawTargets.split(",")).map(String::trim).map(Integer::parseInt).toList();
        return new JoltageRequirement(targetList);
    }

    public int dimension() {
        return targets.size();
    }

    public int getTarget(int index) {
        return targets.get(index);
    }
}