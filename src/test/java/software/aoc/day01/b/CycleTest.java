package software.aoc.day01.b;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CycleTest {
    private static final Cycle CYCLE = new Cycle(100);

    @Test
    public void should_normalize_values_outside_the_range() {
        assertThat(CYCLE.normalize(50)).isEqualTo(50);
        assertThat(CYCLE.normalize(-5)).isEqualTo(95);
        assertThat(CYCLE.normalize(150)).isEqualTo(50);
        assertThat(CYCLE.normalize(0)).isEqualTo(0);
    }

    @Test
    public void should_count_zero_crossings_between_two_raw_positions() {
        assertThat(CYCLE.crossings(50, -18)).isEqualTo(1);
        assertThat(CYCLE.crossings(-18, -48)).isEqualTo(0);
        assertThat(CYCLE.crossings(-48, 0)).isEqualTo(1);
        assertThat(CYCLE.crossings(50, -950)).isEqualTo(10);
        assertThat(CYCLE.crossings(50, 50)).isEqualTo(0);
    }
}
