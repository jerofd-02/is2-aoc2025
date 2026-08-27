package software.aoc.day06.a;

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

        assertThat(worksheet.equations().getFirst().solve()).isEqualTo(33210);
        assertThat(worksheet.equations().get(1).solve()).isEqualTo(490);
        assertThat(worksheet.equations().get(2).solve()).isEqualTo(4243455);
        assertThat(worksheet.equations().getLast().solve()).isEqualTo(401);
    }

    @Test
    public void sum_the_grand_total_of_every_problem() {
        Worksheet worksheet = Worksheet.from(input);

        assertThat(worksheet.grandTotal()).isEqualTo(4277556);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = Worksheet.class.getResourceAsStream("/day06/input.txt")) {
            Worksheet worksheet = Worksheet.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(worksheet.grandTotal()).isEqualTo(6417439773370L);
        }
    }
}
