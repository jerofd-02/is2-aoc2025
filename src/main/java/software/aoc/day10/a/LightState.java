package software.aoc.day10.a;

public record LightState(int mask) {
    public static LightState allOff() {
        return new LightState(0);
    }

    public LightState toggle(Button button) {
        return new LightState(mask ^ button.mask());
    }
}
