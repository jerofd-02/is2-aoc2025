package software.aoc.day11.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReactorNetworkTest {
    private static final String input = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
            """;

    @Test
    public void count_all_paths_from_you_to_out() {
        ReactorNetwork network = ReactorNetwork.from(input);

        assertThat(network.countPathFromYouToOut()).isEqualTo(5);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = ReactorNetwork.class.getResourceAsStream("/day11/input.txt")) {
            ReactorNetwork network = ReactorNetwork.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(network.countPathFromYouToOut()).isEqualTo(470);
        }
    }
}
