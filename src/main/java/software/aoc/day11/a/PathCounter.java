package software.aoc.day11.a;

import software.aoc.day11.CircuitDiagram;

import java.util.HashMap;
import java.util.Map;

public final class PathCounter {
    private final CircuitDiagram diagram;
    private final Map<String, Long> memo = new HashMap<>();

    public PathCounter(CircuitDiagram diagram) {
        this.diagram = diagram;
    }

    public long countPaths(String from, String to) {
        if (from.equals(to)) return 1;
        if (memo.containsKey(from)) return memo.get(from);

        long total = 0;
        for (String next : diagram.outputsOf(from)) total += countPaths(next, to);

        memo.put(from, total);
        return total;
    }
}
