package software.aoc.day10;

import java.util.List;
import java.util.function.Function;

public record FactoryManual<M extends PressCounter>(List<M> machines) {
    public FactoryManual {
        machines = List.copyOf(machines);
    }

    public static <M extends PressCounter> FactoryManual<M> from(String input, Function<String, M> parseMachine) {
        List<M> machines = input.lines().filter(line -> !line.isBlank()).map(parseMachine).toList();
        return new FactoryManual<>(machines);
    }

    public long totalMinPresses() {
        return machines.stream().mapToLong(PressCounter::minPresses).sum();
    }
}
