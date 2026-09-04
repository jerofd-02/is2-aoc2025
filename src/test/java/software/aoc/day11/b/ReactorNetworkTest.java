package software.aoc.day11.b;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReactorNetworkTest {
    private static final String input = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
            """;

    @Test
    public void count_all_paths_from_you_to_out() {
        ReactorNetwork network = ReactorNetwork.from(input);

        assertThat(network.countPathsThroughRequiredDevices("svr", "out", Set.of("dac", "fft"))).isEqualTo(2);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = ReactorNetwork.class.getResourceAsStream("/day11/input.txt")) {
            ReactorNetwork network = ReactorNetwork.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(network.countPathsThroughRequiredDevices("svr", "out", Set.of("dac", "fft"))).isEqualTo(384151614084875L);
        }
    }
}
