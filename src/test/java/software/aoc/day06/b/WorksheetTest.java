package software.aoc.day06.b;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WorksheetTest {
    private static final String input = """
            123 328  51 64\s
             45 64  387 23\s
              6 98  215 314
            *   +   *   + \s
            """;


    @Test
    public void solves_each_individual_problem() {
        Worksheet worksheet = Worksheet.from(input);

        assertThat(worksheet.equations().getFirst().solve()).isEqualTo(1058);
        assertThat(worksheet.equations().get(1).solve()).isEqualTo(3253600);
        assertThat(worksheet.equations().get(2).solve()).isEqualTo(625);
        assertThat(worksheet.equations().getLast().solve()).isEqualTo(8544);
    }

    @Test
    public void sum_the_grand_total_of_every_problem() {
        Worksheet worksheet = Worksheet.from(input);

        assertThat(worksheet.grandTotal()).isEqualTo(3263827);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = Worksheet.class.getResourceAsStream("/day06/input.txt")) {
            Worksheet worksheet = Worksheet.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(worksheet.grandTotal()).isEqualTo(11044319475191L);
        }
    }
}
