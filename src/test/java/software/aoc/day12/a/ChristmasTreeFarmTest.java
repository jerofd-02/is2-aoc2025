package software.aoc.day12.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChristmasTreeFarmTest {
    private static final String input = """
            0:
            ###
            ##.
            ##.
            
            1:
            ###
            ##.
            .##
            
            2:
            .##
            ###
            ##.
            
            3:
            ##.
            ###
            ##.
            
            4:
            ###
            #..
            ###
            
            5:
            ###
            .#.
            ###
            
            4x4: 0 0 0 0 2 0
            12x5: 1 0 1 0 2 2
            12x5: 1 0 1 0 3 2
            """;

    @Test
    public void counts_regions_where_all_presents_fit() {
        ChristmasTreeFarm farm = ChristmasTreeFarm.from(input);

        assertThat(farm.countFittableRegions()).isEqualTo(2);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = ChristmasTreeFarm.class.getResourceAsStream("/day12/input.txt")) {
            ChristmasTreeFarm farm = ChristmasTreeFarm.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(farm.countFittableRegions()).isEqualTo(476);
        }
    }
}
