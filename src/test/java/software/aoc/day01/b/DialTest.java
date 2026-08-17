package software.aoc.day01.b;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DialTest {
    private static final String orders = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
            """;

    @Test
    public void given_orders_should_account_the_final_position() {
        assertThat(Dial.create().position()).isEqualTo(50);
        assertThat(Dial.create().add("L1").position()).isEqualTo(49);
        assertThat(Dial.create().add("L1", "R1", "R50").position()).isEqualTo(0);
        assertThat(Dial.create().add("L51", "L500").position()).isEqualTo(99);
        assertThat(Dial.create().execute(orders).position()).isEqualTo(32);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        assertThat(Dial.create().execute(orders).count()).isEqualTo(6);
        assertThat(Dial.create().add("L1").count()).isEqualTo(0);
        assertThat(Dial.create().add("L1", "R1", "R50").count()).isEqualTo(1);
        assertThat(Dial.create().add("L51", "L500").count()).isEqualTo(6);
        assertThat(Dial.create().add("R1000").count()).isEqualTo(10);
    }

    @Test
    public void password() throws IOException {
        try (InputStream inputStream = DialTest.class.getResourceAsStream("/day01/input.txt")) {
            if (inputStream != null) {
                assertThat(Dial.create().execute(new String(inputStream.readAllBytes())).count()).isEqualTo(6223);
            }
        }
    }
}
