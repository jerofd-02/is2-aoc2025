package software.aoc.day03.b;

import software.aoc.day03.Bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Escalator {
    public static final int DIGITS_TO_SELECT = 12;
    private final List<Bank> banks;
    private final DigitSelectionJoltageCalculator joltage;

    private Escalator(List<Bank> banks, DigitSelectionJoltageCalculator joltage) {
        this.banks = List.copyOf(banks);
        this.joltage = joltage;
    }

    public static Escalator create() {
        return new Escalator(List.of(), DigitSelectionJoltageCalculator.selecting(DIGITS_TO_SELECT));
    }

    public static Escalator create(DigitSelectionJoltageCalculator joltage) {
        return new Escalator(List.of(), joltage);
    }

    public Escalator add(String... newBanks) {
        return withBanks(Arrays.stream(newBanks).map(Bank::from));
    }

    public Escalator add(Bank bank) {
        return withBanks(Stream.of(bank));
    }

    public long totalOutputJoltage() {
        return banks.stream().mapToLong(bank -> bank.maxJoltage(joltage)).sum();
    }

    public Escalator execute(String input) {
        String[] lines = Arrays.stream(input.split("\\R")).map(String::trim).filter(line -> !line.isEmpty()).toArray(String[]::new);
        return add(lines);
    }

    private Escalator withBanks(Stream<Bank> newBanks) {
        List<Bank> merged = new ArrayList<>(banks);
        newBanks.forEach(merged::add);
        return new Escalator(merged, joltage);
    }
}
