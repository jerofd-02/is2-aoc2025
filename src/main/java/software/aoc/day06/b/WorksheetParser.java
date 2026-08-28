package software.aoc.day06.b;

import software.aoc.day06.Equation;
import software.aoc.day06.EquationBuilder;
import software.aoc.day06.Operator;

import java.util.ArrayList;
import java.util.List;

public class WorksheetParser {
    public WorksheetParser() {
    }

    public static List<Equation> parse(String input) {
        List<String> rows = normalizeRows(input);
        List<String> numberRows = rows.subList(0, rows.size() - 1);
        String operatorRow = rows.getLast();

        List<ColumnRange> blocks = findBlocks(numberRows);
        return buildEquations(blocks, numberRows, operatorRow);
    }

    private static List<String> normalizeRows(String input) {
        List<String> rows = input.lines().filter(line -> !line.isBlank()).toList();
        int width = rows.stream().mapToInt(String::length).max().orElse(0);
        return rows.stream().map(row -> padTo(row, width)).toList();

    }

    private static String padTo(String row, int width) {
        return row.length() >= width ? row : row + " ".repeat(width - row.length());
    }

    private static List<ColumnRange> findBlocks(List<String> numberRows) {
        int width = numberRows.getFirst().length();
        List<ColumnRange> blocks = new ArrayList<>();

        int blockStart = -1;
        for (int column = 0; column < width; column++) {
            boolean hasContent = hasContentAt(numberRows, column);
            if (hasContent && blockStart == -1) {
                blockStart = column;
            } else if (!hasContent && blockStart != -1) {
                blocks.add(new ColumnRange(blockStart, column - 1));
                blockStart = -1;
            }
        }
        if (blockStart != -1) {
            blocks.add(new ColumnRange(blockStart, width - 1));
        }
        return blocks;
    }

    private static boolean hasContentAt(List<String> numberRows, int column) {
        return numberRows.stream().anyMatch(row -> !Character.isWhitespace(row.charAt(column)));
    }

    private static List<Equation> buildEquations(List<ColumnRange> blocks, List<String> numberRows, String operatorRow) {
        List<Equation> equations = new ArrayList<>();
        for (int i = blocks.size() - 1; i >= 0; i--) {
            equations.add(buildEquation(blocks.get(i), numberRows, operatorRow));
        }
        return equations;
    }

    private static Equation buildEquation(ColumnRange block, List<String> numberRows, String operatorRow) {
        EquationBuilder builder = new EquationBuilder();
        for (int column = block.end(); column >= block.start(); column--) {
            builder.addNumber(readNumber(numberRows, column));
        }
        return builder.build(Operator.fromSymbol(readOperator(operatorRow, block)));
    }

    private static long readNumber(List<String> numberRows, int column) {
        StringBuilder digits = new StringBuilder();
        for (String row : numberRows) {
            char c = row.charAt(column);
            if (!Character.isWhitespace(c)) digits.append(c);
        }
        return Long.parseLong(digits.toString());
    }

    private static String readOperator(String operatorRow, ColumnRange block) {
        for (int column = block.start(); column <= block.end(); column++) {
            char c = operatorRow.charAt(column);
            if (!Character.isWhitespace(c)) return String.valueOf(c);
        }
        throw new IllegalStateException("No operator found for block " + block);
    }
}
