package software.aoc.day04.a;

import org.junit.Test;
import software.aoc.day04.Position;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GridTest {
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
    public void a_paper_roll_with_four_or_more_neighbors_is_not_accessible() {
        Grid grid = Grid.from(diagram);

        assertThat(grid.isAccessible(new Position(1, 1))).isFalse();
    }

    @Test
    public void a_paper_roll_with_fewer_than_four_neighbors_is_accessible() {
        Grid grid = Grid.from(diagram);

        assertThat(grid.isAccessible(new Position(0, 2))).isTrue();
    }

    @Test
    public void count_all_accessible_paper_rolls() {
        Grid grid = Grid.from(diagram);

        assertThat(grid.accessiblePaperRollsCount()).isEqualTo(13);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = Grid.class.getResourceAsStream("/day04/input.txt")) {
            Grid grid = Grid.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(grid.accessiblePaperRollsCount()).isEqualTo(1419L);
        }
    }
}
