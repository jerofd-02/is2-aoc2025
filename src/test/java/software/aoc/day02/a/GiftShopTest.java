package software.aoc.day02.a;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class GiftShopTest {
    private static final String ranges = """
        11-22
        95-115
        998-1012
        1188511880-1188511890
        222220-222224
        1698522-1698528
        446443-446449
        38593856-38593862
        565653-565659
        824824821-824824827
        2121212118-2121212124
        """;

    @Test
    public void given_a_range_should_sum_invalid_ids() {
        assertThat(GiftShop.create().add("11-22").sumOfInvalidIds()).isEqualTo(33);
        assertThat(GiftShop.create().add("998-1012").sumOfInvalidIds()).isEqualTo(1010);
        assertThat(GiftShop.create().add("1698522-1698528").sumOfInvalidIds()).isEqualTo(0);
        assertThat(GiftShop.create().add("446443-446449").sumOfInvalidIds()).isEqualTo(446446);
    }

    @Test
    public void sum_all_invalid_ids() {
        assertThat(GiftShop.create().execute(ranges).sumOfInvalidIds()).isEqualTo(1227775554);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = GiftShopTest.class.getResourceAsStream("/day02/input.txt")) {
            GiftShop giftShop = GiftShop.create().execute(new String(inputStream != null ? inputStream.readAllBytes() : null));
            assertThat(giftShop.sumOfInvalidIds()).isEqualTo(19219508902L);
        }
    }
}
