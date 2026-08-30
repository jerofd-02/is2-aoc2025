package software.aoc.day08.a;

import software.aoc.day08.DisjointSet;
import software.aoc.day08.JunctionBox;
import software.aoc.day08.PairDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record JunctionBoxNetwork(List<JunctionBox> boxes) {
    public JunctionBoxNetwork {
        boxes = List.copyOf(boxes);
    }

    public static JunctionBoxNetwork from(String input) {
        List<JunctionBox> boxes = input.lines().filter(line -> !line.isBlank()).map(JunctionBox::from).toList();
        return new JunctionBoxNetwork(boxes);
    }

    public long productOfLargestCircuits(int connections, int topN) {
        List<PairDistance> sortedPairs = closestPairsFirst();
        DisjointSet finalState = connectClosest(sortedPairs, connections);
        return finalState.circuitSizes().stream().sorted((a, b) -> Long.compare(b, a)).limit(topN).reduce(1L, (a, b) -> a * b);
    }

    private List<PairDistance> closestPairsFirst() {
        List<PairDistance> pairs = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                pairs.add(new PairDistance(i, j, (long) boxes.get(i).distanceSquaredTo(boxes.get(j))));
            }
        }
        return pairs.stream().sorted().collect(Collectors.toList());
    }

    private DisjointSet connectClosest(List<PairDistance> sortedPairs, int connections) {
        DisjointSet state = DisjointSet.singleTons(boxes.size());
        int limit = Math.min(connections, sortedPairs.size());

        for (int i = 0; i < limit; i++) {
            PairDistance pair = sortedPairs.get(i);
            state = state.union(pair.first(), pair.second());
        }
        return state;
    }
}
