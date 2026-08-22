package software.aoc.day03.b;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class EscalatorTest {
    private static final String banks = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    @Test
    public void given_a_bank_should_find_max_joltage() {
        Escalator escalator = Escalator.create(JoltageCalculator.selecting(2));

        assertThat(escalator.add("987654321111111").totalOutputJoltage()).isEqualTo(98);
        assertThat(escalator.add("811111111111119").totalOutputJoltage()).isEqualTo(89);
        assertThat(escalator.add("234234234234278").totalOutputJoltage()).isEqualTo(78);
        assertThat(escalator.add("818181911112111").totalOutputJoltage()).isEqualTo(92);
    }

    @Test
    public void sum_total_output_joltage_with_two_batteries() {
        Escalator escalator = Escalator.create(JoltageCalculator.selecting(2));

        assertThat(escalator.execute(banks).totalOutputJoltage()).isEqualTo(357);
    }

    @Test
    public void given_a_bank_should_find_max_joltage_with_twelve_batteries() {
        Escalator escalator = Escalator.create(JoltageCalculator.selecting(12));

        assertThat(escalator.add("987654321111111").totalOutputJoltage()).isEqualTo(987654321111L);
        assertThat(escalator.add("811111111111119").totalOutputJoltage()).isEqualTo(811111111119L);
    }

    @Test
    public void sum_total_output_joltage_with_twelve_batteries() {
        Escalator escalator = Escalator.create(JoltageCalculator.selecting(12)).execute(banks);

        assertThat(escalator.totalOutputJoltage()).isEqualTo(3121910778619L);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = Escalator.class.getResourceAsStream("/day03/input.txt")) {
            Escalator escalator = Escalator.create().execute(new String(inputStream != null ? inputStream.readAllBytes() : null));

            assertThat(escalator.totalOutputJoltage()).isEqualTo(173300819005913L);
        }
    }
}
