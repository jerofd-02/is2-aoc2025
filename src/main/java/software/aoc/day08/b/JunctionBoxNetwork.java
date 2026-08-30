package software.aoc.day08.b;

import software.aoc.day08.JunctionBox;
import software.aoc.day08.PairDistance;
import software.aoc.day08.DisjointSet;

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

    public LastConnection lastConnectionToFullyConnect() {
        List<PairDistance> sortedPairs = closestPairsFirst();
        DisjointSet state = DisjointSet.singleTons(boxes.size());
        int remainingCircuits = boxes.size();

        for (PairDistance pair : sortedPairs) {
            DisjointSet next = state.union(pair.first(), pair.second());
            boolean merged = next != state;
            state = next;

            if (merged) {
                remainingCircuits--;
                if (remainingCircuits == 1)
                    return new LastConnection(boxes.get(pair.first()), boxes.get(pair.second()));
            }
        }
        throw new IllegalStateException("The network never became fully connected");
    }

    private List<PairDistance> closestPairsFirst() {
        List<PairDistance> pairs = new ArrayList<>();
        for (int i = 0; i < boxes().size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                pairs.add(new PairDistance(i, j, (long) boxes().get(i).distanceSquaredTo(boxes.get(j))));
            }
        }
        return pairs.stream().sorted().collect(Collectors.toList());
    }
}
