package software.aoc.day04.b;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ForkliftsTest {
    private static final String diagram = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """;

    @Test
    public void keeps_removing_accessible_rolls_until_none_are_left() {
        Grid grid = Grid.from(diagram);

        long totalRemoved = Forklifts.getInstance().totalRemovableRolls(grid);
        assertThat(totalRemoved).isEqualTo(43);
    }

    @Test
    public void removed() throws IOException {
        try (InputStream inputStream = Grid.class.getResourceAsStream("/day04/input.txt")) {
            Grid grid = Grid.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(Forklifts.getInstance().totalRemovableRolls(grid)).isEqualTo(8739L);
        }
    }
}
