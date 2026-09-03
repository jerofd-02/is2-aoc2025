package software.aoc.day10.b;

import java.util.Arrays;
import java.util.List;

public record Button(List<Integer> affectedCounters) {
    public Button {
        affectedCounters = List.copyOf(affectedCounters);
    }

    public static Button from(String rawIndexes) {
        List<Integer> indexes = Arrays.stream(rawIndexes.split(",")).map(String::trim).map(Integer::parseInt).toList();
        return new Button(indexes);
    }

    public boolean affects(int counterIndex) {
        return affectedCounters.contains(counterIndex);
    }
}