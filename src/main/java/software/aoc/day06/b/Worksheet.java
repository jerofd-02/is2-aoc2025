package software.aoc.day06.b;

import software.aoc.day06.Equation;

import java.util.List;

public record Worksheet(List<Equation> equations) {
    public Worksheet {
        equations = List.copyOf(equations);
    }

    public static Worksheet from(String input) {
        return new Worksheet(WorksheetParser.parse(input));
    }

    public long grandTotal() {
        return equations.stream().mapToLong(Equation::solve).sum();
    }
}