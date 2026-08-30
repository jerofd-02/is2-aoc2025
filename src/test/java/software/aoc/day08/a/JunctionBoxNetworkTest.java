package software.aoc.day08.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JunctionBoxNetworkTest {
    public static String input = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
            """;

    @Test
    public void product_of_the_three_largest_circuits_after_ten_connections() {
        JunctionBoxNetwork network = JunctionBoxNetwork.from(input);

        assertThat(network.productOfLargestCircuits(10, 3)).isEqualTo(40);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = JunctionBoxNetwork.class.getResourceAsStream("/day08/input.txt")) {
            JunctionBoxNetwork network = JunctionBoxNetwork.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(network.productOfLargestCircuits(1000, 3)).isEqualTo(79056);
        }
    }
}
