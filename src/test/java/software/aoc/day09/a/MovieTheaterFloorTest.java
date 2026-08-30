package software.aoc.day09.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MovieTheaterFloorTest {
    private static final String input = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            """;

    @Test
    public void finds_the_largest_rectangle_area() {
        MovieTheaterFloor floor = MovieTheaterFloor.from(input);

        assertThat(floor.largestRectangleArea()).isEqualTo(50);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = MovieTheaterFloor.class.getResourceAsStream("/day09/input.txt")) {
            MovieTheaterFloor floor = MovieTheaterFloor.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(floor.largestRectangleArea()).isEqualTo(4729332959L);
        }
    }
}
