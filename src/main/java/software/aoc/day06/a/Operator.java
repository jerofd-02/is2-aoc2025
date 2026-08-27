package software.aoc.day06.a;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

public enum Operator {
    ADD("+", Long::sum, 0L),
    MULTIPLY("*", (a, b) -> a * b, 1L);

    private final String symbol;
    private final LongBinaryOperator function;
    private final long identity;

    Operator(String symbol, LongBinaryOperator function, long identity) {
        this.symbol = symbol;
        this.function = function;
        this.identity = identity;
    }

    public static Operator fromSymbol(String symbol) {
        return Arrays.stream(values()).filter(operator -> operator.symbol.equals(symbol)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unkown operator: " + symbol));
    }

    public long apply(long a, long b) {
        return function.applyAsLong(a, b);
    }

    public long identity() {
        return identity;
    }
}