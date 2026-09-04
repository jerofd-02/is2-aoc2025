package software.aoc.day11.a;

import software.aoc.day11.CircuitDiagram;

public record ReactorNetwork(CircuitDiagram diagram) {
    public static ReactorNetwork from(String input) {
        return new ReactorNetwork(CircuitDiagram.from(input));
    }

    public long countPathFromYouToOut() {
        return new PathCounter(diagram).countPaths("you", "out");
    }
}
