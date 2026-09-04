package software.aoc.day11.b;

import software.aoc.day11.CircuitDiagram;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class RequiredNodePathCounter {
    private final CircuitDiagram diagram;
    private final Set<String> requiredNodes;
    private final Map<String, Long> memo = new HashMap<>();

    public RequiredNodePathCounter(CircuitDiagram diagram, Set<String> requiredNodes) {
        this.diagram = diagram;
        this.requiredNodes = requiredNodes;
    }

    public long countPaths(String from, String to) {
        return countPaths(from, to, VisitedRequiredNodes.none(requiredNodes));
    }

    private long countPaths(String current, String destination, VisitedRequiredNodes visited) {
        VisitedRequiredNodes updated = visited.markVisited(current);

        if (current.equals(destination)) return updated.hasVisitedAll() ? 1 : 0;
        String key = current + "|" + updated.encode();
        if (memo.containsKey(key)) return memo.get(key);

        long total = 0;
        for (String next : diagram.outputsOf(current)) total += countPaths(next, destination, updated);
        memo.put(key, total);
        return total;
    }
}
