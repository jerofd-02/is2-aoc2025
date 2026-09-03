package software.aoc.day10.a;

import java.util.List;

public record FactoryManual(List<Machine> machines) {
    public FactoryManual {
        machines = List.copyOf(machines);
    }

    public static FactoryManual from(String input) {
        List<Machine> machines = input.lines().filter(line -> !line.isBlank()).map(Machine::from).toList();
        return new FactoryManual(machines);
    }

    public long totalMinPresses() {
        return machines.stream().mapToLong(Machine::minPresses).sum();
    }
}
