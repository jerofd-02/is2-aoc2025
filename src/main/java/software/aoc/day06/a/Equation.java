package software.aoc.day06.a;

import java.util.List;

public record Equation(List<Long> numbers, Operator operator) {
    public Equation {
        numbers = List.copyOf(numbers);
    }

    public long solve() {
        return numbers.stream().reduce(operator.identity(), operator::apply);
    }
}
