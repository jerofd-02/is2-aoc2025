package software.aoc.day05.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InventoryTest {
    private static final String input = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32
            """;

    @Test
    public void an_id_outside_every_range_is_spoiled() {
        Inventory inventory = Inventory.from(input);
        assertThat(inventory.freshRanges().isFresh(1)).isFalse();
        assertThat(inventory.freshRanges().isFresh(8)).isFalse();
        assertThat(inventory.freshRanges().isFresh(32)).isFalse();
    }

    @Test
    public void an_inside_a_single_range_is_fresh() {
        Inventory inventory = Inventory.from(input);

        assertThat(inventory.freshRanges().isFresh(5)).isTrue();
        assertThat(inventory.freshRanges().isFresh(11)).isTrue();
    }

    @Test
    public void an_id_inside_overlapping_ranges_is_still_fresh() {
        Inventory inventory = Inventory.from(input);

        assertThat(inventory.freshRanges().isFresh(17)).isTrue();
    }

    @Test
    public void count_all_fresh_available_ingredients() {
        Inventory inventory = Inventory.from(input);

        assertThat(inventory.freshIngredientsCount()).isEqualTo(3);
    }

    @Test
    public void answer() throws IOException {
        try (InputStream inputStream = Inventory.class.getResourceAsStream("/day05/input.txt")) {
            Inventory inventory = Inventory.from(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(inventory.freshIngredientsCount()).isEqualTo(770L);
        }
    }
}
