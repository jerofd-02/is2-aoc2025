package software.aoc.day03.a;

public class JoltageCalculator {
    public long compute(String digits) {
        int[] maxDigitAfter = maxDigitAfterEachPosition(digits);
        return bestTwoDigitValue(digits, maxDigitAfter);
    }

    private int[] maxDigitAfterEachPosition(String digits) {
        int length = digits.length();
        int[] maxDigitAfter = new int[length];
        maxDigitAfter[length - 1] = -1;
        for (int i = length - 2; i >= 0; i--) {
            int nextDigit = digitAt(digits, i + 1);
            maxDigitAfter[i] = Math.max(nextDigit, maxDigitAfter[i + 1]);
        }
        return maxDigitAfter;
    }

    private long bestTwoDigitValue(String digits, int[] maxDigitAfter) {
        long best = -1;
        for (int i = 0; i < digits.length() - 1; i++) {
            long value = digitAt(digits, i) * 10L + maxDigitAfter[i];
            best = Math.max(best, value);
        }
        return best;
    }

    private int digitAt(String digits, int index) {
        return digits.charAt(index) - '0';
    }
}
