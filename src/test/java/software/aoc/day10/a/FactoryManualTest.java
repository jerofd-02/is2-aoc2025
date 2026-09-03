package software.aoc.day10.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FactoryManualTest {
    public static final String input = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    @Test
    public void solves_each_machine_individually() {
        FactoryManual manual = FactoryManual.from(input);

        assertThat(manual.machines().get(0).minPresses()).isEqualTo(2);
        assertThat(manual.machines().get(1).minPresses()).isEqualTo(3);
        assertThat(manual.machines().get(2).minPresses()).isEqualTo(2);
    }

    @Test
    public void sums_the_fewest_button_presses_across_all_machines() {
        FactoryManual manual = FactoryManual.from(input);

        assertThat(manual.totalMinPresses()).isEqualTo(7);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = FactoryManual.class.getResourceAsStream("/day10/input.txt")) {
            FactoryManual manual = FactoryManual.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(manual.totalMinPresses()).isEqualTo(444);
        }
    }
}
