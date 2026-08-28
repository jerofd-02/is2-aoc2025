package software.aoc.day06.a;

import software.aoc.day06.Equation;
import software.aoc.day06.EquationBuilder;
import software.aoc.day06.Operator;

import java.util.ArrayList;
import java.util.List;

public record Worksheet(List<Equation> equations) {
    private static final String WHITESPACE = "\\s+";

    public Worksheet {
        equations = List.copyOf(equations);
    }

    public static Worksheet from(String input) {
        List<String[]> rows = parseRows(input);
        List<String[]> numberRows = rows.subList(0, rows.size() - 1);
        String[] operatorSymbols = rows.getLast();

        return new Worksheet(buildEquations(numberRows, operatorSymbols));
    }

    private static List<String[]> parseRows(String input) {
        return input.lines().map(String::trim).filter(line -> !line.isEmpty()).map(line -> line.split(WHITESPACE)).toList();
    }

    private static List<Equation> buildEquations(List<String[]> numberRows, String[] operatorSymbols) {
        List<EquationBuilder> builders = newBuilders(operatorSymbols.length);
        for (String[] numberRow : numberRows) {
            for (int column = 0; column < numberRow.length; column++) {
                builders.get(column).addNumber(Long.parseLong(numberRow[column]));
            }
        }

        List<Equation> equations = new ArrayList<>();
        for (int column = 0; column < operatorSymbols.length; column++) {
            Operator operator = Operator.fromSymbol(operatorSymbols[column]);
            equations.add(builders.get(column).build(operator));
        }
        return equations;
    }

    private static List<EquationBuilder> newBuilders(int count) {
        List<EquationBuilder> builders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            builders.add(new EquationBuilder());
        }
        return builders;
    }

    public long grandTotal() {
        return equations.stream().mapToLong(Equation::solve).sum();
    }
}