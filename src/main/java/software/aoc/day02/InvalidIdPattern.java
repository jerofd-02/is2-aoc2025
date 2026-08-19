package software.aoc.day02;

import java.util.regex.Pattern;

public class InvalidIdPattern {
    private final Pattern pattern;

    public InvalidIdPattern(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public static InvalidIdPattern of(String regex) {
        return new InvalidIdPattern(regex);
    }

    public boolean matches(long id) {
        return pattern.matcher(Long.toString(id)).matches();
    }
}
