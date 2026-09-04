package software.aoc.day11.b;

import software.aoc.day11.CircuitDiagram;

import java.util.Set;

public record ReactorNetwork(CircuitDiagram diagram) {
    public static ReactorNetwork from(String input) {
        return new ReactorNetwork(CircuitDiagram.from(input));
    }

    public long countPathsThroughRequiredDevices(String from, String to, Set<String> requiredDevices) {
        return new RequiredNodePathCounter(diagram, requiredDevices).countPaths(from, to);
    }
}
