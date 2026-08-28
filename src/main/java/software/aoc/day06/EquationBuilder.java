package software.aoc.day06;

import java.util.ArrayList;
import java.util.List;

public final class EquationBuilder {
    private final List<Long> numbers = new ArrayList<>();

    public EquationBuilder addNumber(long number) {
        numbers.add(number);
        return this;
    }

    public Equation build(Operator operator) {
        return new Equation(numbers, operator);
    }
}
