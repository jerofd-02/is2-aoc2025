package software.aoc.day03.b;

import software.aoc.day03.JoltageCalculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class DigitSelectionJoltageCalculator implements JoltageCalculator {
    private final int digitsToSelect;

    public DigitSelectionJoltageCalculator(int digitsToSelect) {
        this.digitsToSelect = digitsToSelect;
    }

    public static DigitSelectionJoltageCalculator selecting(int digitsToSelect) {
        return new DigitSelectionJoltageCalculator(digitsToSelect);
    }

    @Override
    public long compute(String digits) {
        return Long.parseLong(selectMaxDigits(digits));
    }

    private String selectMaxDigits(String digits) {
        Deque<Character> selected = new ArrayDeque<>();
        int discardsRemaining = digits.length() - digitsToSelect;

        for (char digit : digits.toCharArray()) {
            while (canDiscardWorseDigit(selected, discardsRemaining, digit)) {
                selected.removeLast();
                discardsRemaining--;
            }
            selected.addLast(digit);
        }
        return keepFirst(selected, digitsToSelect);
    }

    private boolean canDiscardWorseDigit(Deque<Character> selected, int discardsRemaining, char digit) {
        return !selected.isEmpty() && discardsRemaining > 0 && selected.peekLast() < digit;
    }

    private String keepFirst(Deque<Character> selected, int count) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (char digit : selected) {
            if (index++ >= count) {
                break;
            }
            result.append(digit);
        }
        return result.toString();
    }
}