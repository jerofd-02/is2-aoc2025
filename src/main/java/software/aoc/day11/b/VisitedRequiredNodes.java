package software.aoc.day11.b;

import java.util.Set;
import java.util.TreeSet;

public record VisitedRequiredNodes(Set<String> requiredNodes, Set<String> visited) {
    public VisitedRequiredNodes {
        requiredNodes = Set.copyOf(requiredNodes);
        visited = Set.copyOf(visited);
    }

    public static VisitedRequiredNodes none(Set<String> requiredNodes) {
        return new VisitedRequiredNodes(requiredNodes, Set.of());
    }

    public VisitedRequiredNodes markVisited(String node) {
        if (!requiredNodes.contains(node) || visited.contains(node)) return this;
        Set<String> newVisited = new TreeSet<>(visited);
        newVisited.add(node);
        return new VisitedRequiredNodes(requiredNodes, newVisited);
    }

    public boolean hasVisitedAll() {
        return visited.containsAll(requiredNodes);
    }

    public String encode() {
        return String.join(",", new TreeSet<>(visited));
    }
}
